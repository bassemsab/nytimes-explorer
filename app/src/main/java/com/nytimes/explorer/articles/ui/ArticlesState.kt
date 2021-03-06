package com.nytimes.explorer.articles.ui

import com.nytimes.explorer.articles.data.model.search.Article


data class ArticlesState(
    var articles: List<Article> = emptyList(),
    var page: Int = 1,
    var isLoading: Boolean = false,
    var isSearching: Boolean = false,
)
