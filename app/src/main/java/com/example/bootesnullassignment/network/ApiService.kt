package com.example.bootesnullassignment.network

import com.example.bootesnullassignment.models.News
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("/api/1/news")
    fun get_news(
        @Query("apikey") apiKey: String, @Query("language") language: String,
        @Query("page") page: Int
    ): Call<News>
}