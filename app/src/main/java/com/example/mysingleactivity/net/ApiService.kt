package com.example.mysingleactivity.net

import com.example.mysingleactivity.models.RedditApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("best/.json")
    suspend fun getAllBest(
        @Query("limit") loadSize: Int = 20,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Response<RedditApiResponse>

}