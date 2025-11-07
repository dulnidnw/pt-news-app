package com.example.pt_news_app.presentation.ui.profile

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pt_news_app.data.datastore.UserPreferences
import com.example.pt_news_app.data.local.db.NewsDatabase
import com.example.pt_news_app.data.repository.UserRepositoryImpl

@Composable
fun profileVmFactory(): ViewModelProvider.Factory {
    val context = LocalContext.current
    val app = context.applicationContext as Application
    val db = NewsDatabase.get(app)
    val repository = UserRepositoryImpl(db.userDao())
    val preferences = UserPreferences(context)

    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(repository, preferences) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
