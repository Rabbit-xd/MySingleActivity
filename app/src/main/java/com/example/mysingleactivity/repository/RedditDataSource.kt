package com.example.mysingleactivity.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.net.ApiService

class RedditDataSource(
    private var api: ApiService
    ): PagingSource<String,Post>() {
    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return null
    }
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        try{
            val limit = params.loadSize
            val after = params.key
            val response = api.getAllBest(limit, after)

            if(response.isSuccessful){
                val data = response.body()?.data
                val items = data?.children?.map { it.data } ?: emptyList()
                val nextKEy = data?.after

                return LoadResult.Page(items,null,nextKEy)

            } else {
                return LoadResult.Error(Exception("Failed to load posts"))
            }

        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

}