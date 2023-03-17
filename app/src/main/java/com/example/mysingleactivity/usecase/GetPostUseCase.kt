package com.example.mysingleactivity.usecase

import androidx.paging.PagingData
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.repository.RedditRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val redditRepository: RedditRepository
) {

    fun execute() : Flow<PagingData<Post>> {
        return redditRepository.getBestPosts()
    }

}