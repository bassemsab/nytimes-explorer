package com.nytimes.explorer.articles.ui

import com.nytimes.explorer.articles.data.model.Article


data class ArticlesState(
    var articles: List<Article> = emptyList(),
    var isLoading: Boolean = false

)
