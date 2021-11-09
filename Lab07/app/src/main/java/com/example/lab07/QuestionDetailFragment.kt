package com.example.lab07

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.lab07.models.MyViewModel


class QuestionDetailFragment : Fragment() {
    private lateinit var viewModel: MyViewModel
    lateinit var questionTV: TextView
    lateinit var anser1TV: TextView
    lateinit var anser2TV: TextView
    lateinit var anser3TV: TextView
    lateinit var anser4TV: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_detail, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        questionTV = view.findViewById(R.id.detailQuestionTV)
        questionTV.setText(viewModel.getItem().text)

        anser1TV = view.findViewById(R.id.detailAnswer1TV)
        anser1TV.setText(viewModel.getItem().answers.get(0).answer)

        anser2TV = view.findViewById(R.id.detailAnswer2TV)
        anser2TV.setText(viewModel.getItem().answers.get(1).answer)

        anser3TV = view.findViewById(R.id.detailAnswer3TV)
        anser3TV.setText(viewModel.getItem().answers.get(2).answer)

        anser4TV = view.findViewById(R.id.detailAnswer4TV)
        anser4TV.setText(viewModel.getItem().answers.get(3).answer)
        return view
    }


}