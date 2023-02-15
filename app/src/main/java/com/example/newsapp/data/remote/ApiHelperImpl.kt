package com.example.newsapp.data.remote

import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun getArticles(source: String,page: Int)= flow { emit(apiService.getArticlesNews(source,page,100)) }
}