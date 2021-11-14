package com.example.lab06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.lab06.controllers.QuizController
import com.example.lab06.models.MyViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var topAppBar : MaterialToolbar
    lateinit var drawerLayout : DrawerLayout
    lateinit var navigationView : NavigationView
    lateinit var quizController : QuizController
    lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeView()
        initMenu()
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        quizController = QuizController(this)
        //loads the questions into the viewmodel

        initViewModel()

    }

    private fun initViewModel() {
        myViewModel.setQuestions(quizController.questions)
        myViewModel.setNumOfTotalAnswers(quizController.totalAnswerNum)
        Log.d("totalAnswerNum : ", "${quizController.totalAnswerNum}")
        Log.d("xxx", " initializing viewmodel in MainActivity")
    }

    private fun initializeView(){
        topAppBar = findViewById(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
    }
    private fun initMenu(){
        //nem igazán tudom a topAppBar miért nem működik, vagy hogy kéne működjön
        topAppBar.setNavigationOnClickListener{
            drawerLayout.open()
        }


        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            when(menuItem.itemId) {
                R.id.homeMenuItem -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFragment)
                R.id.quizMenuItem -> Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.quizStartFragment)
                R.id.profileMenuItem -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.profileFragment)
            }
            menuItem.setChecked(false)
            drawerLayout.close()
            true
        }

    }
}
