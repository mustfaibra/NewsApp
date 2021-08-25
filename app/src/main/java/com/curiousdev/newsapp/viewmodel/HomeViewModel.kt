package com.curiousdev.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.curiousdev.newsapp.model.ApiResponse
import com.curiousdev.newsapp.model.Article
import com.curiousdev.newsapp.repos.NewsRepo
import retrofit2.Call

class HomeViewModel : ViewModel() {

    fun getAllHeadlinesOnline() : Call<ApiResponse>{
        return NewsRepo.getHeadlinesFromApi()
    }

    fun getAllHeadlinesOffline(context: Context) : LiveData<MutableList<Article>>{
        return NewsRepo.getHeadlinesFromLocal(context)
    }

    suspend fun deleteAllOfflineHeaders(context: Context){
        return NewsRepo.deleteAllOfflineHeaders(context)
    }

    fun checkIfDataExist(context: Context) : LiveData<Int>{
        return NewsRepo.getSavedDataCount(context)
    }

    suspend fun keepArticlesLocally(context: Context, articles: List<Article>){
        NewsRepo.keepArticlesLocally(context, articles)
    }


}