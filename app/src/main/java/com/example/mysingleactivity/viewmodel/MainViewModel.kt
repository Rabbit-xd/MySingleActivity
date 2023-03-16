package com.example.mysingleactivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.net.ApiClient
import com.example.mysingleactivity.net.ApiService
import com.example.mysingleactivity.repository.RedditDataSource
import kotlinx.coroutines.flow.Flow

//@HiltViewModel
class MainViewModel : ViewModel() {
    private val api = ApiClient.getClient().create(ApiService::class.java)

    fun getBestPost() : Flow<PagingData<Post>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            )
        ){
            RedditDataSource(api)
        }.flow.cachedIn(viewModelScope) //.flow
    }

    private val _sharedUrl = MutableLiveData<String>()
    val sharedUrl: LiveData<String> = _sharedUrl

    private fun updateShareUrl (url : String ){
        _sharedUrl.value = url
    }


}