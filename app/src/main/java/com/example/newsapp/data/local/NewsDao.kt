package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.model.Article

@Dao
interface NewsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList( article: List<Article>):List<Long>


    @Query("SELECT * FROM  Article")
    fun getAllArticles(): List<Article>

    @Query("DELETE FROM Article")
    suspend fun deleteAllNews()


}