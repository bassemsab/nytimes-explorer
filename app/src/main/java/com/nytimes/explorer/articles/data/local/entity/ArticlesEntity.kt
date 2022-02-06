package com.nytimes.explorer.articles.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nytimes.explorer.articles.data.model.search.Article


@Entity
data class ArticlesEntity(
    val keyword: String,
    val articles: List<Article>,
    @PrimaryKey val id: Int? = null
)