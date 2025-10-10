package com.example.pt_news_app.data.remote.dto

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
