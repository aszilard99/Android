package com.example.lab03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class QuizActivity : AppCompatActivity() {
    lateinit var quizController : QuizController
    lateinit var questionTextView : TextView
    lateinit var rButton0 : RadioButton
    lateinit var rButton1 : RadioButton
    lateinit var rButton2 : RadioButton
    lateinit var rButton3 : RadioButton
    lateinit var nextQuestionButton : Button
    lateinit var question: Question
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizController = QuizController(this)
        quizController.shuffleQuestions()
        setContentView(R.layout.activity_quiz)

        questionTextView = findViewById(R.id.questionTextView)
        rButton0 = findViewById(R.id.button0)
        rButton1 = findViewById(R.id.button1)
        rButton2 = findViewById(R.id.button2)
        rButton3 = findViewById(R.id.button3)

        nextQuestionButton = findViewById(R.id.nextQuestionButton)

        val tempQuestion = quizController.nextQuestion()
        if (tempQuestion == null){
            startResultsScreenActivity()
        }
        else {
            question = tempQuestion
            Log.d("answers", "shuffled ")
            question.answers.shuffle()
            questionTextView.text = question.text
            rButton0.text = question.answers.get(0).answer
            rButton1.text = question.answers.get(1).answer
            rButton2.text = question.answers.get(2).answer
            rButton3.text = question.answers.get(3).answer
            nextQuestionButton.visibility = View.INVISIBLE

            rButton0.setOnClickListener { onRButtonClick(rButton0) }
            rButton1.setOnClickListener { onRButtonClick(rButton1) }
            rButton2.setOnClickListener { onRButtonClick(rButton2) }
            rButton3.setOnClickListener { onRButtonClick(rButton3) }
            nextQuestionButton.setOnClickListener { showNextQuestion() }
        }

    }

    private fun showNextQuestion(){
        evaluateAnswer()
        val tmp = quizController.nextQuestion()
        if (tmp == null){
            startResultsScreenActivity()
        }
        else {
            question = tmp
            questionTextView.text = question.text
            Log.d("answers", "shuffled ")
            question.answers.shuffle()
            rButton0.text = question.answers.get(0).answer
            rButton1.text = question.answers.get(1).answer
            rButton2.text = question.answers.get(2).answer
            rButton3.text = question.answers.get(3).answer
        }
    }
    private fun evaluateAnswer() {
        //will store which answer was selected
        var answerNumber = -1
        if (rButton0.isChecked){
            answerNumber = 0
        }
        if (rButton1.isChecked){
            answerNumber = 1
        }
        if (rButton2.isChecked){
            answerNumber = 2
        }
        if (rButton3.isChecked){
            answerNumber = 3
        }
        if (question.answers.get(answerNumber).isValid){
            Log.d("answer validity", "correct")
            val toast = Toast.makeText(this,"correct", Toast.LENGTH_SHORT)
            toast.show()
            quizController.increaseCorrectAnswerNum()
        }
        else{
            Log.d("answer validity", "incorrect")
            val toast = Toast.makeText(this,"incorrect", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
    /***
    Makes sure that the player can only go to the next question if he/she already selected one answer on the current question
     ***/
    private fun onRButtonClick(v : RadioButton){
        if (nextQuestionButton.isVisible && !v.isChecked){
            nextQuestionButton.visibility = View.INVISIBLE
        }
        else {
            nextQuestionButton.visibility = View.VISIBLE
        }
    }
    private fun startResultsScreenActivity(){
        val intent = Intent(this, ResultScreenActivity::class.java).apply {
            putExtra("correctAnswerNum", quizController.getCorrectAnswerNum().toString())
            putExtra("totalQuestionNum", quizController.getTotalQuestionNum().toString())
        }
        startActivity(intent)
    }

}