package com.example.lab07.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab07.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private  val repository: Repository): ViewModel() {
    val myResponse: MutableLiveData<Response<Post>> = MutableLiveData()
    lateinit var iterator : ListIterator<Json_question>
    var currentPosition: Int = 0

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
            
        }
    }
}