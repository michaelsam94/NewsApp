package com.example.newsapp.data.remote

import com.example.newsapp.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getArticles(source:String,page: Int): Flow<NewsResponse>
}