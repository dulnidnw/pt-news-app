package com.example.pt_news_app.domain.repository

import com.example.pt_news_app.domain.model.User


interface UserRepository {
    suspend fun create(firstName: String, lastName: String, email: String, password: String): Result<User>
    suspend fun login( email: String, password: String): Result<User>
//    suspend fun createUser(user: User)
    suspend fun getUserByEmail(email: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}