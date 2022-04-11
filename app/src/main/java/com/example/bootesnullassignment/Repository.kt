package com.example.bootesnullassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bootesnullassignment.models.News
import com.example.bootesnullassignment.network.ApiClient
import com.example.bootesnullassignment.network.ApiService
import retrofit2.Retrofit

class Repository(private val apiService: ApiService) {

    fun getNews() = apiService.get_news("pub_6374e01347247d7e7c33f1426130dca65ade", "en", 1)
}