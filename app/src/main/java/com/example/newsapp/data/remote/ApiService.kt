package com.example.newsapp.data.remote

import com.example.newsapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getArticlesNews(@Query("sources") sourceName: String,@Query("page")page: Int,@Query("pageSize") pageSize: Int): NewsResponse
}