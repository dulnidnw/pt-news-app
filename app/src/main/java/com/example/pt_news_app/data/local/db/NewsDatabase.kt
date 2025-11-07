package com.example.pt_news_app.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pt_news_app.data.local.dao.FavoriteDao
import com.example.pt_news_app.data.local.dao.UserDao
import com.example.pt_news_app.data.local.entity.Favorite
import com.example.pt_news_app.data.local.entity.UserEntity
import kotlin.jvm.java

@Database(entities = [UserEntity::class, Favorite::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null
//        val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL(
//                    """
//            CREATE TABLE IF NOT EXISTS `favorite` (
//                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                `title` TEXT NOT NULL,
//                `subtitle` TEXT,
//                `body` TEXT,
//                `imageUrl` TEXT,
//                `author` TEXT,
//                `publishAt` TEXT,
//                `content` TEXT
//            )
//            """.trimIndent()
//                )
//            }
//        }
        fun get(context: Context): NewsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database.db"
                )/*.addMigrations(MIGRATION_2_3)*/
                    .build().also { INSTANCE = it }
            }

    }



    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
}