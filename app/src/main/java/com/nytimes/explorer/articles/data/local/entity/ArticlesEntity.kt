package com.nytimes.explorer.articles.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nytimes.explorer.articles.data.local.Converters
import com.nytimes.explorer.articles.data.model.Article


@Entity
@TypeConverters(Converters::class)
data class ArticlesEntity(
    val keyword: String,
    val articles: List<Article>,
    @PrimaryKey val id: Int? = null
)