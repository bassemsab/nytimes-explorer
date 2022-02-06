package com.nytimes.explorer.articles.data.local

import androidx.room.*
import com.nytimes.explorer.articles.data.local.entity.ArticlesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(Articles: ArticlesEntity)

    @Query("DELETE FROM articlesEntity WHERE keyword = :keyword")
    suspend fun deleteArticles(keyword: String)


    @Query("SELECT * FROM articlesEntity WHERE keyword = :keyword")
    suspend fun getArticles(keyword: String): ArticlesEntity


}