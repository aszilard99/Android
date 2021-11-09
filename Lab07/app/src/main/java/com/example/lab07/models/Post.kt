package com.example.lab07.models



data class Post(
    val response_code : Int,
    val results: MutableList<Json_question>
)

data class Json_question(
    val category : String,
    val type : String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: MutableList<String>
)