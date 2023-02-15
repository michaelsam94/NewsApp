package com.example.newsapp.data.remote

import android.util.Log
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip

class RetrofitOnlineDataSource(private val service: ApiHelper): OnlineDataSource {
    private val articlesNews = mutableListOf<Article>()

    companion object {
        var errorMsg: String = ""
    }

    override suspend fun getArticles(page: Int): List<Article> {
        if(page == 1) articlesNews.clear()
        service.getArticles("the-next-web",page)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                errorMsg = e.message.toString()
                Log.e("TAG", errorMsg)
            }
            .collect {
                articlesNews.addAll(it.articles ?: emptyList())
            }

        return articlesNews

    }

}