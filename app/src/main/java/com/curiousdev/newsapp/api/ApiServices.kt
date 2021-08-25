package com.curiousdev.newsapp.api

import com.curiousdev.newsapp.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("top-headlines?apiKey=5ae54b078b8f4258bf248a2db0703b95")
    fun getNewsHeadlines(
        @Query("country") country: String
    ) : Call<ApiResponse>
}