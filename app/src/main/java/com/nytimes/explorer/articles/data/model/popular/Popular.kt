package com.nytimes.explorer.articles.data.model.popular

import com.nytimes.explorer.articles.data.model.search.Article
import com.nytimes.explorer.articles.data.model.search.Byline
import com.nytimes.explorer.articles.data.model.search.Headline
import com.nytimes.explorer.articles.data.model.search.Multimedia
import com.nytimes.explorer.articles.ui.Article

data class Popular(
    val results: List<Result>,
) {

    fun toArticles(): MutableList<Article> {

        val artilces: MutableList<Article> = mutableListOf()

        results.forEach {
            var imageUrl =
                it.media.firstOrNull { it1 ->
                    !it1.mediaMetadata.firstOrNull { it2 -> !it2.url.isNullOrBlank() }?.url.isNullOrBlank()
                }?.mediaMetadata?.first()?.url

            var mMedia = listOf(Multimedia(imageUrl ?: ""))

            artilces.add(
                Article(
                    headline = Headline(it.title),
                    multimedia = mMedia,
                    byline = Byline(it.byline),
                    pub_date = it.published_date,
                    web_url = it.url

                )
            )
        }
        return artilces
    }
}