package com.example.mysingleactivity.viewmodel

/**
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
import com.example.mysingleactivity.repository.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class RedditViewModel(private val repository: RedditRepository) : ViewModel() {

  // fun loadPosts(): Flow<PagingData<Post>> {
  //     return repository.getBestPosts().cachedIn(viewModelScope)
  // }
  var apiService = ApiClient.getClient().create(ApiService::class.java)


   private val _posts = MutableLiveData<PagingData<Post>>()
   val posts: LiveData<PagingData<Post>>
       get() = _posts
   init {
       getBestPost()
   }
    fun getBestPost () {
        viewModelScope.launch {
            val pagingConfig = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            )
            val pagingSource = RedditDataSource(apiService)
            val data = Pager(pagingConfig){pagingSource}.flow.cachedIn(viewModelScope)
            // _posts.postValue(posts)
        }
    }

}


**/