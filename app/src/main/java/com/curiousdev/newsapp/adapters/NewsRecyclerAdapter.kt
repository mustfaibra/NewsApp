package com.curiousdev.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.curiousdev.newsapp.R
import com.curiousdev.newsapp.databinding.NewsRecyclerItemBinding
import com.curiousdev.newsapp.interfaces.OnArticleClick
import com.curiousdev.newsapp.model.Article
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class NewsRecyclerAdapter : RecyclerView.Adapter<NewsRecyclerAdapter.ArticleHolder>() {

    private var articles : MutableList<Article> = mutableListOf<Article>()
    public lateinit var onArticleClick: OnArticleClick

    // our holder that holder the news article
    inner class ArticleHolder(val itemBinding: NewsRecyclerItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val itemBinding = NewsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val article = articles[position]
        Picasso.get().load(article.image).resize(360,360).into(holder.itemBinding.articleImg,object: Callback{
            override fun onSuccess() {
                holder.itemBinding.imageLoader.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                holder.itemBinding.imageLoader.visibility = View.GONE
                Picasso.get().load(R.drawable.cover).resize(360,360).into(holder.itemBinding.articleImg)
            }

        })
        holder.itemBinding.article = article
        // setting click event handler
        holder.itemBinding.root.setOnClickListener{
            // when user click on an article , pass the ImageView that show article's image & and the article object itself
            onArticleClick.onArticleClicked(holder.itemBinding.articleImg, article,position)
        }
    }

    override fun getItemCount() = articles.size

    /**
     * helper methods for editing our recycler dynamically
     * useful in case there are a large set of data retrieved from the api and its paginated !
     */

    private fun add(article : Article){
        this.articles.add(article)
        notifyItemInserted(articles.size-1)
    }

    public fun push(articles: List<Article>){
        articles.forEach { article->
            add(article)
        }
    }
}