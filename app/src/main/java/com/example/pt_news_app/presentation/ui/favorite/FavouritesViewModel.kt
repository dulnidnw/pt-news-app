package com.example.pt_news_app.presentation.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pt_news_app.data.local.db.NewsDatabase
import com.example.pt_news_app.data.local.entity.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel (application: Application) : AndroidViewModel(application) {

    private val favoriteDao = NewsDatabase.get(application).favoriteDao()


    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = favoriteDao.getAllFavorites()
        }
    }
}