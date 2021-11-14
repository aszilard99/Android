package com.example.lab06

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab06.models.MyViewModel

class QuestionAddFragment : Fragment() {
    private lateinit var viewModel : MyViewModel

    private lateinit var questionTV : TextView
    private lateinit var answer0TV : TextView
    private lateinit var answer1TV : TextView
    private lateinit var answer2TV : TextView
    private lateinit var answer3TV : TextView

    private lateinit var addButton : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question_add, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view : View){
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        questionTV = view.findViewById(R.id.addQuestionTV)
        answer0TV = view.findViewById(R.id.addAnswer0TV)
        answer1TV = view.findViewById(R.id.addAnswer1TV)
        answer2TV = view.findViewById(R.id.addAnswer2TV)
        answer3TV = view.findViewById(R.id.addAnswer3TV)
        addButton = view.findViewById(R.id.addQuestionFragmentButton)
        addButton.setOnClickListener{
            val tmp = questionTV.text
            val tmp1 = !questionTV.text.isEmpty()
            if (!questionTV.text.isEmpty() && !answer0TV.text.isEmpty() && !answer1TV.text.isEmpty() && !answer2TV.text.isEmpty() && !answer3TV.text.isEmpty()){
                viewModel.addQuestion(questionTV.text.toString(),answer0TV.text.toString(),answer1TV.text.toString(),answer2TV.text.toString(),answer3TV.text.toString())
            }
            else {
                val toast = Toast.makeText(requireContext(), "every field has to be completed", Toast.LENGTH_SHORT)
                toast.show()

            }
        }
    }
}