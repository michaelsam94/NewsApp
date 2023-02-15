package com.example.newsapp.di

import android.util.Log.VERBOSE
import androidx.room.Room
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.local.OfflineDataSource
import com.example.newsapp.data.local.RoomOfflineDataSource
import com.example.newsapp.data.remote.*
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.utils.NetworkAwareHandler
import com.example.newsapp.utils.NetworkHandler
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val roomModule = module {
    single {
        Room.databaseBuilder(get(), NewsDatabase::class.java, "NEW_DB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<NewsDatabase>().getNewsDao() }
}

val networkModule = module {
    single {
        Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.API_KEY)
                        .build()
                )
                .build()
            return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
        }
    }

    single {
        LoggingInterceptor.Builder()
            .setLevel(Level.BODY)
            .log(VERBOSE)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<LoggingInterceptor>())
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    single {
        GsonConverterFactory.create()
    }

    single {
        CoroutineCallAdapterFactory()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
            .client(get<OkHttpClient>())
            .build()
    }
}

val apiServiceModule = module {
    factory {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val repoModule = module {
    single { NewsRepository(get() , get() , get() ) }

    factory  <OfflineDataSource>{ RoomOfflineDataSource(get()) }

    factory <OnlineDataSource> { RetrofitOnlineDataSource(get())  }

    single <NetworkAwareHandler> { NetworkHandler(get())  }

    factory <ApiHelper> { ApiHelperImpl(get())  }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get())}
}