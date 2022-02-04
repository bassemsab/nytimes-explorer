package com.nytimes.explorer.articles.data.remote

import com.nytimes.explorer.articles.data.model.ArticleSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface NytApi {


    @GET("/svc/search/v2/articlesearch.json")
    suspend fun getArticleSearch(
        @Query("q") text: String,
        @Query("api-key") apiKey: String,
    ): ArticleSearch


    companion object {
        const val BASE_URL = "https://api.nytimes.com/"
    }

}