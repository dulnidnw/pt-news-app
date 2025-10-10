package com.example.pt_news_app

import android.app.Application
import androidx.room.Room
import com.example.pt_news_app.data.local.db.NewsDatabase
//import dagger.hilt.android.HiltAndroidApp
//
//@HiltAndroidApp
class MainApplication : Application(){

    companion object{
        lateinit var newsDatabse: NewsDatabase
    }
    override fun onCreate(){
        super.onCreate()
        Room.databaseBuilder(
            applicationContext,
            NewsDatabase::class.java,
            "news_db.db"
        ).build()
    }
}