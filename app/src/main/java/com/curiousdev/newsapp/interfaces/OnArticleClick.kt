package com.curiousdev.newsapp.interfaces

import android.widget.ImageView
import com.curiousdev.newsapp.model.Article

interface OnArticleClick {
    fun onArticleClicked(image: ImageView,article: Article,position: Int)
}