package com.example.lab06.models

import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private var playerName = ""
    private var highScore = 0
    private var numOfCorrectAnswers = 0
    private var numOfTotalAnswers = 0
    private var questions  = mutableListOf<Question>()
    lateinit var iterator : ListIterator<Question>

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
}