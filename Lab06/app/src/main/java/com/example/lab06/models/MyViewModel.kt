package com.example.lab06.models

import android.util.Log
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private var playerName = ""
    private var highScore = 0
    private var numOfCorrectAnswers = 0
    private var numOfTotalAnswers = 0
    private var questions  = mutableListOf<Question>()
    lateinit var iterator : ListIterator<Question>
    var currentPosition: Int = 0

    fun getItem() : Question{
        return questions[currentPosition]
    }

    fun deleteQuestion(pos: Int){
        questions.removeAt(pos)
        setQuestions(questions)
        numOfTotalAnswers--
    }

    fun setQuestions(q : MutableList<Question>){
        questions = q.toMutableList()
        iterator = questions.listIterator()

    }
    fun resetIterator(){
        iterator = questions.listIterator()
    }
    fun nextQuestion() : Question? {
        if (!iterator.hasNext()){
            return null
        }
        return iterator.next()
    }

    fun incrementNumOfCorrectAnswers(){
         numOfCorrectAnswers++
    }
    fun setNumOfTotalAnswers(n : Int){

        numOfTotalAnswers = n

    }
    fun getNumOfCorrectAnswers() : Int {
        return numOfCorrectAnswers
    }
    fun getNumOfTotalAnswers() : Int{
        return numOfTotalAnswers
    }
    fun setCorrectAnswerNum(n : Int){
        numOfCorrectAnswers = n
    }
    fun setPlayerName(str: String){
        playerName = str
    }
    fun checkAndSetHighScore(n : Int){
        if (n > highScore){
            highScore = n
        }
    }
    fun resetHighScore(){
        highScore = 0
    }
    fun getPlayerName() : String{
        return playerName
    }
    fun getHighScore() : Int{
        return highScore
    }
    fun shuffleQuestions(){
        questions.shuffle()
    }
    fun getList(): MutableList<Question> = questions

    fun addQuestion(q: String, a0:String, a1:String,a2:String,a3:String){
        val answer0 = Answer(a0, true)
        val answer1 = Answer(a1, false)
        val answer2 = Answer(a2, false)
        val answer3 = Answer(a3, false)
        val answers = mutableListOf(answer0,answer1,answer2,answer3)
        val question = Question(q, answers)
        questions.add(questions.size,question)
        Log.d("xxx", "question added, number of questions: ${questions.size}")
        iterator = questions.listIterator()
    }
}