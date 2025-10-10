package com.example.pt_news_app.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pt_news_app.data.local.dao.UserDao
import com.example.pt_news_app.data.local.entity.UserEntity
import kotlin.jvm.java

@Database(entities = [UserEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null
        fun get(context: Context): NewsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_db.db"
                ).build().also { INSTANCE = it }
            }
    }

    abstract fun userDao(): UserDao
}