package com.example.lab07.models

data class Question(val category : String, val type : QuestionType, val difficulty: Difficulty , val text: String, val answers: MutableList<Answer>)


enum class Difficulty(){
    EASY,
    MEDIUM,
    HARD
}

enum class QuestionType(){
    MultipleChoice,
    TrueFalse
}