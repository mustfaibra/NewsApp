package com.curiousdev.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

@Entity(tableName = "articles")
data class Article (
    @PrimaryKey(autoGenerate = true) var id: Int,

    @SerializedName("author")
    @Expose val author: String? = null,

    @SerializedName("title")
    @Expose val title: String? = null,

    @SerializedName("description")
    @Expose val description: String? = null,

    @SerializedName("url")
    @Expose val url: String? = null,

    @SerializedName("urlToImage")
    @Expose val image: String? = null,

    @SerializedName("publishedAt")
    @Expose val publishedAt: String? = null,

)