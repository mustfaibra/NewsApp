package com.curiousdev.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.curiousdev.newsapp.model.Article
import com.curiousdev.newsapp.repos.NewsRepo

class DetailsViewModel : ViewModel() {

    public fun getArticleById(context: Context ,id: Int): LiveData<Article>{
        return NewsRepo.getArticleById(context, id)
    }
}