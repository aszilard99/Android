package com.example.lab06.controllers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab06.R
import com.example.lab06.models.MyViewModel
import com.example.lab06.models.Question
import java.lang.reflect.Type


class QuestionFragment : Fragment() {

    lateinit var quizController : QuizController
    lateinit var questionTextView : TextView
    lateinit var radioGroup: RadioGroup
    lateinit var rButton0 : RadioButton
    lateinit var rButton1 : RadioButton
    lateinit var rButton2 : RadioButton
    lateinit var rButton3 : RadioButton
    lateinit var nextQuestionButton : Button
    lateinit var question: Question
    lateinit var layout : RelativeLayout
    private val questionNum = 4

    private lateinit var myViewModel : MyViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_questionFragment_to_quizStart)
                myViewModel.setCorrectAnswerNum(0)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        view?.apply {
            Log.d("xxx", "QuestionFragment onCreateView ran down")
            myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
            initializeQuestionFragment(this)

        }
        return view
    }

    @SuppressLint("ResourceType")
    private fun initializeQuestionFragment(view : View) {
        //quizController = QuizController(view)
        //quizController.shuffleQuestions()
        Log.d("questions","shuffled")
        questionTextView = view.findViewById(R.id.questionTextView)
        questionTextView.textSize = 18f
        //var params0 = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //params0.setMargins(50,50,50,0)
       //questionTextView.layoutParams = params0
        //questionTextView.setTypeface(null, Typeface.BOLD)
        layout = view.findViewById(R.id.layout)

        radioGroup = RadioGroup(view.context)
        radioGroup.layoutParams



        var params1 = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params1.setMargins(25,125,50,0)

        rButton0 = RadioButton(view.context)
        rButton0.layoutParams = params1

        rButton0.textSize = 16f
        rButton0.setTypeface(null, Typeface.BOLD)
        rButton0.id = 0

        rButton1 = RadioButton(view.context)
        rButton1.textSize = 16f
        rButton1.setTypeface(null, Typeface.BOLD)
        rButton1.layoutParams = params1
        rButton1.id = 1

        rButton2 = RadioButton(view.context)
        rButton2.textSize = 16f
        rButton2.setTypeface(null, Typeface.BOLD)
        rButton2.layoutParams = params1
        rButton2.id = 2

        rButton3 = RadioButton(view.context)


        rButton3.layoutParams = params1
        rButton3.textSize = 16f
        rButton3.setTypeface(null, Typeface.BOLD)
        rButton3.id = 3

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(10, 0, 10, 0)
        radioGroup.layoutParams = params

        radioGroup.addView(rButton0)
        radioGroup.addView(rButton1)
        radioGroup.addView(rButton2)
        radioGroup.addView(rButton3)

        layout.addView(radioGroup)

        nextQuestionButton = view.findViewById(R.id.nextQuestionButton)

        val tempQuestion = myViewModel.nextQuestion()
        if (tempQuestion == null){
            startQuizEndFragment()
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

        val tmp = myViewModel.nextQuestion()
        if (tmp == null || myViewModel.iterator.nextIndex() > questionNum){
            startQuizEndFragment()
            myViewModel.resetIterator()
            myViewModel.shuffleQuestions()
        }
        else {
            question = tmp
            question.answers.shuffle()
            Log.d("answers", "shuffled")
            questionTextView.text = question.text
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

            myViewModel.incrementNumOfCorrectAnswers()
        }
        else{
            Log.d("answer validity", "incorrect")

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

    private fun startQuizEndFragment(){
        findNavController().navigate(R.id.action_questionFragment_to_quizEndFragment)
    }



}