package com.nytimes.explorer.articles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nytimes.explorer.articles.data.local.entity.ArticlesEntity

@Database(
    entities = [ArticlesEntity::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class ArticlesDataBase : RoomDatabase() {
    abstract val dao: ArticlesDao
}