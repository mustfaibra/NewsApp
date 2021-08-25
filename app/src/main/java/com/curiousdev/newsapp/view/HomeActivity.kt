package com.curiousdev.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.curiousdev.newsapp.R
import com.curiousdev.newsapp.adapters.NewsRecyclerAdapter
import com.curiousdev.newsapp.databinding.ActivityHomeBinding
import com.curiousdev.newsapp.interfaces.OnArticleClick
import com.curiousdev.newsapp.model.ApiResponse
import com.curiousdev.newsapp.model.Article
import com.curiousdev.newsapp.utils.NetworkHelper
import com.curiousdev.newsapp.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), OnArticleClick {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: NewsRecyclerAdapter

    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        //set rtl layout
        ViewCompat.setLayoutDirection(binding.root, ViewCompat.LAYOUT_DIRECTION_RTL)
        setContentView(binding.root)
        // initiate the view model
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // initiate our adapter and implement the interface methods
        adapter = NewsRecyclerAdapter()
        adapter.onArticleClick = this
        // set our recycler's adapter
        binding.newsRecycelr.adapter = adapter
        // getting the data from the api
        if (NetworkHelper.checkIfConnected(this)){
            // device is connected
            getArticlesWhileConnected()
        } else {
            // device is offline
            getArticleWhileOffline()
        }

    }

    private fun getArticlesWhileConnected(){
        viewModel.getAllHeadlinesOnline().enqueue(object: Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
                // hide the loading bar and show the recycler view that hold the news
                binding.isLoadingCompleted = true
                if (response == null) {
                    Toast.makeText(this@HomeActivity,getString(R.string.some_err_occur),Toast.LENGTH_LONG).show()
                } else {
                    val articles = response.body()?.articles
                    adapter.push(articles!!)
                    // using coroutines so that we don't block the ui thread
                    lifecycleScope.launch {
                        // Then we save that response into our local database , but first we delete all out of date data
                        viewModel.deleteAllOfflineHeaders(this@HomeActivity)
                        // we should save the updates now !
                        viewModel.keepArticlesLocally(this@HomeActivity,articles)
                    }
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getArticleWhileOffline() {
        Toast.makeText(this,getString(R.string.make_sure_internet_on),Toast.LENGTH_LONG).show()
        // then we check if there are some articles saved locally to the database
        viewModel.getAllHeadlinesOffline(this).observe(this, { articles->
            binding.isLoadingCompleted = true
            if (articles.size > 0) {
                Log.d(TAG, "getArticleWhileOffline: offline articles count is " + articles.size)
                adapter.push(articles)
            } else {
                Toast.makeText(this,getString(R.string.no_local_articles),Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * An overridden method of OnArticleClick interface that send user from home to details activity
     *  It get invoked when the user click on an article
     */
    override fun onArticleClicked(image: ImageView, article: Article,position: Int) {
        val toDetailsIntent = Intent(this,DetailsActivity::class.java)
        toDetailsIntent.putExtra("id",position+1)
        // setting the shared element transition
        image.transitionName = "articleImg"
        val option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(image,"articleImg"))
        // fire !
        startActivity(toDetailsIntent,option.toBundle())
    }
}