package com.example.newsapp.data.remote

import com.example.newsapp.data.model.Article

interface OnlineDataSource {
    suspend fun getArticles(page: Int): List<Article> = emptyList()
}