package com.example.lab06.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.lab06.R
import com.example.lab06.models.MyViewModel


class ProfileFragment : Fragment() {

    lateinit var nameET: EditText
    lateinit var highScoreTV: TextView
    lateinit var changeNameButton: Button
    private val myViewModel : MyViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view?.apply{
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view : View) {
        nameET = view.findViewById(R.id.playerNameET)
        highScoreTV = view.findViewById(R.id.highScoreTV)
        changeNameButton = view.findViewById(R.id.changeNameButton)
        changeNameButton.setOnClickListener {
            myViewModel.setPlayerName(nameET.text.toString())
            myViewModel.resetHighScore()
        }
        nameET.setText("${myViewModel.getPlayerName()}")
        highScoreTV.setText("High score: ${myViewModel.getHighScore()}")
    }


}