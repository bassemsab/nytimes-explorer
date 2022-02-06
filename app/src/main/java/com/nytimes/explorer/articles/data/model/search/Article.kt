package com.nytimes.explorer.articles.data.model.search


data class Article(
    val _id: String? = null,
    val headline: Headline,
    val multimedia: List<Multimedia>,
    val byline: Byline,
    val pub_date: String,
    val web_url: String,
)