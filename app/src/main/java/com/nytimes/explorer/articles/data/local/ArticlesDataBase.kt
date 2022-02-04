package com.nytimes.explorer.articles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nytimes.explorer.articles.data.local.entity.ArticlesEntity

@Database(
    entities = [ArticlesEntity::class],
    version = 1
)
abstract class ArticlesDataBase : RoomDatabase() {
    abstract val dao: ArticlesDao
}