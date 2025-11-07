package com.example.pt_news_app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsSource(
    val id: String?,
    val name: String
) : Parcelable
