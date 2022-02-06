package com.nytimes.explorer.articles.data.repository

import com.nytimes.explorer.core.util.Resource
import com.nytimes.explorer.articles.data.local.ArticlesDao
import com.nytimes.explorer.articles.data.local.entity.ArticlesEntity
import com.nytimes.explorer.articles.data.model.search.Article
import com.nytimes.explorer.articles.data.remote.NytApi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ArticlesRepository(
    private val api: NytApi,
    private val dao: ArticlesDao
) {
    fun getArticles(keyword: String, media: String): Flow<Resource<List<Article>>> = flow {
        val articles = dao?.getArticles(keyword)?.articles

        emit(Resource.Loading(articles))

        try {

            val remoteDocs = if (media.isNotBlank()) {
                val social = if (media == NytApi.MOST_SHARED) NytApi.FACEBOOK_SOCIAL else ""
                api.getPopularArticles(media, social).toArticles()

            } else {
                api.getArticleSearch(keyword, 0).response.docs
            }


            emit(Resource.Success(remoteDocs))

            dao.deleteArticles(keyword)
            dao.insertArticles(ArticlesEntity(keyword, remoteDocs))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message(), data = articles))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server, check your internet connection", data = articles))

        }
        val newArticles = dao?.getArticles(keyword)?.articles
        emit(Resource.Success(newArticles))

    }


}