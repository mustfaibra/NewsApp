package com.curiousdev.newsapp.repos

import android.content.Context
import androidx.lifecycle.LiveData
import com.curiousdev.newsapp.api.RetrofitClient
import com.curiousdev.newsapp.model.ApiResponse
import com.curiousdev.newsapp.model.Article
import com.curiousdev.newsapp.room.RoomClient
import retrofit2.Call

object NewsRepo {

    private val services = RetrofitClient.services

    public fun getHeadlinesFromApi() : Call<ApiResponse> {
        return services.getNewsHeadlines("ae")
    }

    suspend fun keepArticlesLocally(context: Context, articles:List<Article>){
        articles.forEach { article -> RoomClient.getRoomInstance(context).getDao().addArticle(article) }
    }


    public fun getHeadlinesFromLocal(context: Context) : LiveData<MutableList<Article>>{
        return RoomClient.getRoomInstance(context).getDao().getAllArticles()
    }

    suspend fun deleteAllOfflineHeaders(context: Context) {
        return RoomClient.getRoomInstance(context).getDao().deleteAllArticles()
    }

    public fun getSavedDataCount(context: Context) : LiveData<Int>{
        return RoomClient.getRoomInstance(context).getDao().getAllArticlesCount()
    }

    public fun getArticleById(context: Context, id: Int) : LiveData<Article>{
        return RoomClient.getRoomInstance(context).getDao().getArticleById(id)
    }

}