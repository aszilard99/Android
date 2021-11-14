package com.example.lab06.controllers

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.lab06.R
import com.example.lab06.models.MyViewModel
import androidx.activity.OnBackPressedCallback

import androidx.navigation.fragment.findNavController



class QuizEndFragment : Fragment() {

    lateinit var resultTv : TextView
    private val myViewModel : MyViewModel by activityViewModels()
    lateinit var tryAgainB : Button

    //on pressing back
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_quizEndFragment_to_quizStart)
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
        val view =  inflater.inflate(R.layout.fragment_quiz_end, container, false)
        view?.apply{
            initializeEndFragment(this)
        }
        return view
    }
    private fun initializeEndFragment(view: View){
        resultTv = view.findViewById(R.id.resTextView)
        tryAgainB = view.findViewById(R.id.tryAgainButton)

        tryAgainB.setOnClickListener{
            myViewModel.setCorrectAnswerNum(0)
            findNavController().navigate(R.id.action_quizEndFragment_to_quizStart)
        }
        var total = myViewModel.getNumOfTotalAnswers()
        var correct = myViewModel.getNumOfCorrectAnswers()
        //reseting correct answer number
        myViewModel.setCorrectAnswerNum(0)
        resultTv.setText("You answered $correct correctly out of 4")
        myViewModel.checkAndSetHighScore(correct)

    }

}