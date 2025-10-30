package com.example.pt_news_app.domain.usecase

import android.R.attr.category
import android.R.attr.country
import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.domain.repository.NewsRepository
import kotlin.collections.filter

//class SearchNewsUseCase(private val repo: NewsRepository) {
class SearchNewsUseCase(private val repo: NewsRepository) {

//    suspend operator fun invoke(newsArticles: String) =
//        repo.getNewsFeed(newsArticles)


    operator fun invoke(allArticles: List<Article>, query: String): List<Article> {
        return if (query.isEmpty()) {
            allArticles
        } else {
            allArticles.filter {
                it.title?.contains(query, ignoreCase = true) == true
            }
        }
    }
    suspend fun getFilterNewsFeed() = repo.getFilterNewsFeed()
}