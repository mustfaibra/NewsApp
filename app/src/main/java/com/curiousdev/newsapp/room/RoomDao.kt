package com.curiousdev.newsapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.curiousdev.newsapp.model.Article

@Dao
interface RoomDao {
    @Insert
    suspend fun addArticle(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles() : LiveData<MutableList<Article>>

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()

    @Query("SELECT count(*) FROM articles")
    fun getAllArticlesCount() : LiveData<Int>

    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticleById(id: Int): LiveData<Article>
}