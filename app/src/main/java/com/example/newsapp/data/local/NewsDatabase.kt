package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.model.Article

@Database(entities = [Article::class], version = 1 , exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}