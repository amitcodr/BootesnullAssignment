package com.example.bootesnullassignment.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bootesnullassignment.Constants
import com.example.bootesnullassignment.Repository
import com.example.bootesnullassignment.models.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    lateinit var repository: Repository

    constructor(repository: Repository) : this() {
        this.repository = repository
    }

    private val newsData = MutableLiveData<News>()
    private val isLoading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private val fabClicked = MutableLiveData<Boolean>()


    val liveNews: LiveData<News>
        get() = newsData

    val loading: LiveData<Boolean> = isLoading
    val errorMessage: LiveData<String> = error
    val fabClick: LiveData<Boolean> = fabClicked


    fun setFabClick() {
//        fabClicked.postValue(true)
        getNewsList()
    }


    fun getNewsList() {
        isLoading.postValue(true)
        val result = repository.getNews()
        result.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    if (response.body()?.results?.isEmpty() == true)
                        error.postValue(Constants.NO_CONTENT)
                    newsData.postValue(response.body())
                    isLoading.postValue(false)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                isLoading.postValue(false)
                error.postValue(Constants.NO_SERVER)
            }

        })

    }


}