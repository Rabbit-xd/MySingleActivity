package com.example.mysingleactivity.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.usecase.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RedditViewModel @Inject constructor(private val getPostUseCase: GetPostUseCase) : ViewModel() {
    fun getBestPosts(): Flow<PagingData<Post>> {
        return getPostUseCase.execute()
            .cachedIn(viewModelScope)
    }
}

