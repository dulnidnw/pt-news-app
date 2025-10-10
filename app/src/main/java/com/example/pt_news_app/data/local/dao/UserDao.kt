package com.example.pt_news_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pt_news_app.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun registerUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :username AND password = :password LIMIT 1")
    fun loginUser(username: String, password: String): UserEntity?

    @Query("SELECT COUNT(*) FROM users WHERE email = :username")
    fun isUserExists(username: String): Int

}