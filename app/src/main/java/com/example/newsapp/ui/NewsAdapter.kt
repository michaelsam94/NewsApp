package com.example.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.utils.dateFormat
import com.squareup.picasso.Picasso

class NewsAdapter (private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val newsList = mutableListOf<Article>()

    fun addArticlesToList(articles: List<Article>) {
        newsList.addAll(articles)
        notifyDataSetChanged()
    }

    fun clearArticles(){
        newsList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return NewsViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(newsList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(private val binding: ItemNewsBinding, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Article) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            Picasso.get().load(item.urlToImage)
                .into(binding.articleImage)
            binding.titleTxt.text = item.title
            binding.authorNameTxt.text = "By ${item.author} "
            binding.dateTxt.text = "${dateFormat(item.publishedAt)}"
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Article)
    }
}
