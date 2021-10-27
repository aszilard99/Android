package com.example.lab05.models

import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private var numOfCorrectAnswers = 0
    private var numOfTotalAnswers = 0
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

}