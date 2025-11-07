package com.example.pt_news_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val subtitle: String?,
    val body: String?,
    val imageUrl: String?,
    val author: String?,
    val publishAt: String?,
    val content: String?


)
