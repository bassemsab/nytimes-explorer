package com.nytimes.explorer.articles.data.repository

import android.util.Log
import com.nytimes.explorer.core.util.Resource
import com.nytimes.explorer.articles.data.local.ArticlesDao
import com.nytimes.explorer.articles.data.local.entity.ArticlesEntity
import com.nytimes.explorer.articles.data.model.Article
import com.nytimes.explorer.articles.data.remote.NytApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ArticlesRepository(
    private val api: NytApi,
    private val dao: ArticlesDao
) {
    fun getArticles(keyword: String): Flow<Resource<List<Article>>> = flow {
//        emit(Resource.Loading(emptyList()))
//        val articles = dao.getArticles(keyword).articles
//        emit(Resource.Loading(articles))
        try {
            Log.d("testBassem", "before${String}")

            val remoteDocs = api.getArticleSearch(keyword, "N3AhIAieRGr8KA0iIGGAg1DVFgpvX4Bw")?.response?.docs
            emit(Resource.Success(remoteDocs))


            dao.deleteArticles(keyword)
            dao.insertArticles(ArticlesEntity(keyword, remoteDocs))
        } catch (e: HttpException) {
//            emit(Resource.Error(message = e.message(), data = articles))
        } catch (e: IOException) {
//            emit(Resource.Error("Couldn't reach the server, check your internet connection", data = articles))

        }

//        val newArticles = dao.getArticles(keyword).articles

    }


}