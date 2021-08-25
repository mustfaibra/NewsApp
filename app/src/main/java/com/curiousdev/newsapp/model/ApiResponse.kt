package com.curiousdev.newsapp.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.curiousdev.newsapp.model.Article

class ApiResponse(
    @SerializedName("status")
    @Expose val status: String? = null,
    @SerializedName("totalResults")
    @Expose val totalResults: Int? = null,
    @SerializedName("articles")
    @Expose val articles: List<Article>? = null
)