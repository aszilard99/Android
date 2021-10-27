package com.example.lab05

import android.util.Log
import android.view.View
import com.example.lab05.models.Answer
import com.example.lab05.models.MyViewModel
import com.example.lab05.models.Question
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class QuizController(var view: View) {
    val questions = mutableListOf<Question>()
    var iterator: ListIterator<Question>
    var totalAnswerNum = 0

    init{
        val isreader : InputStream = view.resources.openRawResource(R.raw.questions)
        val reader = BufferedReader(InputStreamReader(isreader))
        Log.d("Input", "reading starts here")
        while(true){
            try {
                var line = reader.readLine()
                if (line == null) {
                    break
                }
                val question : String = line
                Log.d("input question", question)
                line = reader.readLine()
                if (line == null) {
                    break
                }
                /*the protocol is that when reading in, always the first answer is the correct one**/
                val answer1 = Answer(line, true)
                Log.d("input answer", answer1.answer)
                line = reader.readLine()
                if (line == null) {
                    break
                }
                val answer2 = Answer(line, false)
                Log.d("input answer", answer2.answer)
                line = reader.readLine()
                if (line == null) {
                    break
                }
                val answer3 = Answer(line, false)
                Log.d("input answer", answer3.answer)
                line = reader.readLine()
                if (line == null) {
                    break
                }
                val answer4 = Answer(line, false)
                Log.d("input answer", answer4.answer)
                val answerList = mutableListOf(answer1, answer2, answer3, answer4)
                val q = Question(question, answerList)
                increaseCorrectAnswerNum()
                questions.add(q)
            }catch(e:IOException){
                e.printStackTrace()
            }

        }
        Log.d("Input", "end")
        iterator = questions.listIterator()
    }


    fun nextQuestion() : Question? {
        if (!iterator.hasNext()){
            return null
        }
        return iterator.next()
    }
    fun prevQuestion() : Question? {
        if (!iterator.hasPrevious()){
            return null
        }
        return iterator.previous()
    }
    fun increaseCorrectAnswerNum(){
        totalAnswerNum++
    }

    fun shuffleQuestions(){
        questions.shuffle()
    }
}
