package com.nytimes.explorer.articles.data.model.popular

data class Result(
    val byline: String,
    val media: List<Media>,
    val published_date: String,
    val title: String,
    val url: String
)