package com.curiousdev.newsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.curiousdev.newsapp.R
import com.curiousdev.newsapp.databinding.ActivityDetailsBinding
import com.curiousdev.newsapp.utils.NetworkHelper
import com.curiousdev.newsapp.viewmodel.DetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // bind the layout's view to our activity
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        //set rtl layout
        ViewCompat.setLayoutDirection(binding.root, ViewCompat.LAYOUT_DIRECTION_RTL)
        setContentView(binding.root)

        // initiate view model
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // get id sent with the intent with setting the default to 1
        val articleId = intent.getIntExtra("id",1)
        detailsViewModel.getArticleById(this,articleId).observe(this, Observer { article->
            if (article != null) {
                // binding our article to the view
                binding.article = article
                Picasso.get().load(article.image).into(binding.articleImg)
            }
        })
    }

    override fun onBackPressed() {
        //To support reverse transitions when user clicks the device back button
        supportFinishAfterTransition()
    }
}