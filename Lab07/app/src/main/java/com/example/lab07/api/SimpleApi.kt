package com.example.lab07.api

import com.example.lab07.models.Post
import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {
    @GET("api.php?amount=10")
    suspend fun getPost(): Response<Post>

}