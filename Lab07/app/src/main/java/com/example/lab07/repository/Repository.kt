package com.example.lab07.repository

import com.example.lab07.api.RetrofitInstance
import com.example.lab07.models.Post
import retrofit2.Response

class Repository {
    suspend fun getPost() : Response<Post> {
        return RetrofitInstance.api.getPost()
    }
}