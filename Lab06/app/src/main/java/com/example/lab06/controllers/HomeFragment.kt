package com.example.lab06.controllers

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.lab06.R


class Home : Fragment() {

    lateinit var startQuizButton : Button
    lateinit var questionListButton : Button
    lateinit var questionAddButton : Button
    // a fragmenteknek saját viselkedést adtam a back gomb megnyomásakor, és
    // valószínűleg a back stacken emiatt kavarodás van és a home fragmentből
    // enélkül nem lépne ki rendesen back gombbal
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                //lezárja az activityt -> az appot is mert 1 activityből áll
                activity?.finishAndRemoveTask()
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
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        view?.apply{
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view : View) {
        startQuizButton = view.findViewById(R.id.startQuizButton)
        questionListButton = view.findViewById(R.id.questionListButton)
        questionAddButton = view.findViewById(R.id.homeQuestionAddB)
        startQuizButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_quizStart)
        }
        questionListButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_questionListFragment)
        }
        questionAddButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_questionAddFragment)
        }
    }


}