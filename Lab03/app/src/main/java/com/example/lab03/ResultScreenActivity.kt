package com.example.lab03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_screen)
        val correctAnswerNum = intent.getStringExtra("correctAnswerNum")
        val totalQuestionNum = intent.getStringExtra("totalQuestionNum")
        val resTextView = findViewById<TextView>(R.id.resTextView)
        resTextView.setText("$correctAnswerNum correct answers out of $totalQuestionNum total questions")

    }
}