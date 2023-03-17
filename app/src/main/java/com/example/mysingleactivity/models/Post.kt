package com.example.mysingleactivity.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class Post(
    @SerializedName("name")
    val key : String?,
    val title : String?,
    val score: Int?,
    val author: String?,
    @SerializedName("num_comments")
    val commentCount : Int?,
    var url: String?,
    //var subreddit: String?,
    //var selftext: String?,
    var thumbnail : String?,
    @SerializedName("id")
    var postId : String?,
    //@SerializedName("author_fullname")
    //var authName: String?,
    @SerializedName("subreddit_name_prefixed")
    var subredditNamePrefixed: String?
) : Parcelable