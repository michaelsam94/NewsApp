package com.example.newsapp.data.local

import com.example.newsapp.data.model.Article

class RoomOfflineDataSource(private val newsDao: NewsDao) : OfflineDataSource {

    override fun getArticles(): List<Article> = newsDao.getAllArticles()

    override suspend fun cacheArticles(data: List<Article>) {
        newsDao.insertList(data)
    }

    override suspend fun deleteAllNews() {
        newsDao.deleteAllNews()
    }
}