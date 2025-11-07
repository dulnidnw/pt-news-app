package com.example.pt_news_app.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val source: NewsSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
): Parcelable{

    val formattedDateTime: String
        get() = try {
            val inputFormat = java.text.SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                java.util.Locale.getDefault()
            )
            inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")

            val outputFormat = java.text.SimpleDateFormat(
                "MMM dd, yyyy hh:mm a",
                java.util.Locale.getDefault()
            )
            outputFormat.timeZone = java.util.TimeZone.getDefault() // device local time

            val date = inputFormat.parse(publishedAt)
            if (date != null) outputFormat.format(date) else publishedAt
        } catch (e: Exception) {
            publishedAt
        }
}
