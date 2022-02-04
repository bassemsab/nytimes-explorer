package com.nytimes.explorer.articles.data.local

import androidx.room.*
import com.nytimes.explorer.articles.data.local.entity.ArticlesEntity


@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(Articles: ArticlesEntity)

    @Query("DELETE FROM ArticlesEntity WHERE keyword = :keyword")
    suspend fun deleteArticles(keyword: String)

    @Query("SELECT * FROM ArticlesEntity WHERE keyword = :keyword")
    suspend fun getArticles(keyword: String): ArticlesEntity

}