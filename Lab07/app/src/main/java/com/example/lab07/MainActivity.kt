package com.example.lab07

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.lab07.controllers.QuizController
import com.example.lab07.models.MainViewModel
import com.example.lab07.models.MyViewModel
import com.example.lab07.repository.Repository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var topAppBar : MaterialToolbar
    lateinit var drawerLayout : DrawerLayout
    lateinit var navigationView : NavigationView
    lateinit var quizController : QuizController
    lateinit var myViewModel: MyViewModel

    //using this viewModel after implementing retrofit
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer{ response ->
            if(response.isSuccessful){
                Log.d("response", response.body()?.response_code.toString())
                Log.d("response", response.body()?.results.toString())
            }else{
                Log.d("response - error", response.errorBody().toString())
            }


        })


        initializeView()
        initMenu()
        /*myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        quizController = QuizController(this)
        //loads the questions into the viewmodel
        initViewModel()*/

    }

    private fun initViewModel() {
        myViewModel.setQuestions(quizController.questions)
        myViewModel.setNumOfTotalAnswers(quizController.totalAnswerNum)
        Log.d("totalAnswerNum : ", "${quizController.totalAnswerNum}")
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
