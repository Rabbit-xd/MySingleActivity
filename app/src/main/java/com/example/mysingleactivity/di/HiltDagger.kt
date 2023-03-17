package com.example.mysingleactivity.di

import com.example.mysingleactivity.net.ApiEndPoint.BASE_URL
import com.example.mysingleactivity.net.ApiService
import com.example.mysingleactivity.repository.RedditRepository
import com.example.mysingleactivity.usecase.GetPostUseCase
import com.example.mysingleactivity.viewmodel.RedditViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule{
    @Provides
    @Singleton
    fun provideRedditApiService(): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetPostUseCase(redditRepository: RedditRepository) : GetPostUseCase{
        return GetPostUseCase(redditRepository)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule{

    @Provides
    fun provideRedditViewModel( getPostUseCase: GetPostUseCase) : RedditViewModel{
        return RedditViewModel(getPostUseCase)
    }
}

@InstallIn(SingletonComponent::class)
@Module
object MyModule{

    @Provides
    @Singleton
    fun provideRepository(): RedditRepository{
        return RedditRepository()
    }

}


