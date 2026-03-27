package com.example.pt_news_app.domain.repository

import com.example.pt_news_app.domain.model.User


interface UserRepository {
    suspend fun create(firstName: String, lastName: String, email: String, password: String): Result<User>
    suspend fun login( email: String, password: String): Result<User>
}