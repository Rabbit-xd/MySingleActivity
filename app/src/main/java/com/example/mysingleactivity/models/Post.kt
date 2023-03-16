package com.example.mysingleactivity.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(title)
        parcel.writeValue(score)
        parcel.writeString(author)
        parcel.writeValue(commentCount)
        parcel.writeString(url)
        parcel.writeString(thumbnail)
        parcel.writeString(postId)
        parcel.writeString(subredditNamePrefixed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}
