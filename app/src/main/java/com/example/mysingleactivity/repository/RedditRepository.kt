package com.example.mysingleactivity.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.net.ApiEndPoint.BASE_URL
import com.example.mysingleactivity.net.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditRepository{
    private val apiRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
    fun getBestPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {RedditDataSource(apiRetrofit)}
        ).flow
    }
}