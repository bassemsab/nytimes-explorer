package com.nytimes.explorer.articles.data.remote

import com.nytimes.explorer.BuildConfig
import com.nytimes.explorer.articles.data.model.popular.Popular
import com.nytimes.explorer.articles.data.model.search.ArticleSearch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NytApi {


    @GET("/svc/search/v2/articlesearch.json")
    suspend fun getArticleSearch(
        @Query("q") text: String,
        @Query("page") page: Int? = 0,
        @Query("api-key") apiKey: String = BuildConfig.NYT_API_KEY

    ): ArticleSearch

    @GET("/svc/mostpopular/v2/{media}/7{social}.json")
    suspend fun getPopularArticles(
        @Path("media") media: String,
        @Path("social") social: String,
        @Query("api-key") apiKey: String = BuildConfig.NYT_API_KEY

    ): Popular


    companion object {
        const val MOST_SHARED = "shared"
        const val MOST_VIEWED = "viewed"
        const val MOST_EMAILED = "emailed"
        const val FACEBOOK_SOCIAL = "/facebook"

        const val BASE_URL = "https://api.nytimes.com/"
    }

}