package com.example.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.model.Article
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var articleNews = MutableLiveData<Resource<Article>>()

    init {
        getHomeNews(1)
    }

    fun getHomeNews(page: Int) {
        articleNews.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = newsRepository.getNewsSources(page)
            articleNews.postValue(Resource.Success(result))
            if(result.isNullOrEmpty()){
                articleNews.postValue(Resource.Error(msg="No data saved "))
            }
        }
    }

    fun getNews() = articleNews as LiveData<Resource<Article>>

}