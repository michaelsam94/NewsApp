package com.example.newsapp.data.local

import com.example.newsapp.data.model.Article

interface OfflineDataSource {
    fun getArticles(): List<Article> = emptyList()

    suspend fun cacheArticles(data: List<Article>){}

    suspend fun deleteAllNews(){}

}