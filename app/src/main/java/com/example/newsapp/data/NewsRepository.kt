package com.example.newsapp.data

import com.example.newsapp.data.local.OfflineDataSource
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.remote.OnlineDataSource
import com.example.newsapp.utils.NetworkAwareHandler

class NewsRepository(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val networkHandler: NetworkAwareHandler
) {



    suspend fun getNewsSources(page: Int) : List<Article> {

        return if (networkHandler.isOnline()) {
            val data = onlineDataSource.getArticles(page)
            if(page == 1) offlineDataSource.deleteAllNews()
            offlineDataSource.cacheArticles(data)
            data
        } else {
            offlineDataSource.getArticles()
        }
    }

}