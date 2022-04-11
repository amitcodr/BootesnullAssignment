package com.example.bootesnullassignment.models

import android.icu.text.CaseMap

data class News(
    val nextPage: String,
    val results: List<Result>
)

data class Result(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val image_url: String,
    val source_id: String
)
