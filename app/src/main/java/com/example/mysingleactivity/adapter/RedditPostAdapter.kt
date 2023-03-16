package com.example.mysingleactivity.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysingleactivity.R
import com.example.mysingleactivity.databinding.ItemDataModelBinding
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.ui.HomeFragmentDirections
import com.example.mysingleactivity.utils.DiffUtilCallBack

class RedditPostAdapter (private val listener: PostClickListener)
     : //onClickListener: OnClickListener) :
        PagingDataAdapter<Post, RedditPostAdapter.PostViewHolder>(DiffUtilCallBack())  {
        lateinit var navController: NavHostController

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_model,parent,false)
            val binding = ItemDataModelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return PostViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post = getItem(position)
            post?.let { holder.bind(it,listener) }
        }


        //    class PostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        class PostViewHolder(
            private val binding: ItemDataModelBinding
        ) :
            RecyclerView.ViewHolder(binding.root) {
            //binding = ItemDataModelBinding.bind(binding)
            //  private val scoreText = binding.tvScore
            //  private val commentText= binding.tvComments
            private val titleText = binding.tvTitle
            private val subText = binding.tvSubreddit
            private val authorText = binding.tvAuthor
            private var thumbnailView = binding.imageView
            fun bind (post: Post, listener: RedditPostAdapter.PostClickListener){
                with(post) {
                    itemView.setOnClickListener {
                        listener.onPostClick(post)
                    }
                    binding.tvScore.text = score.toString()
                    binding.tvComments.text = commentCount.toString()
                    titleText.text= title
                    subText.text = subredditNamePrefixed
                    authorText.text = author
                    val imageURL = post.thumbnail
                    if (imageURL != "self") {
                        if (post.thumbnail?.startsWith("http") == true) {
                            thumbnailView.visibility = View.VISIBLE
                            Glide.with(thumbnailView)
                                .load(imageURL)
                                .error(R.drawable.baseline_clear_24)
                                .into(thumbnailView)
                        } else {
                            Glide.with(thumbnailView)
                                .load(imageURL)
                                .error(R.drawable.baseline_clear_24)
                                .into(thumbnailView)
                        }
                    }else {
                        thumbnailView.visibility = View.GONE
                    }

                }

            }

        }
     interface PostClickListener{
         //@SuppressLint("ResourceType")
         fun onPostClick(post: Post)//{
             //navController = findNavController()
            // val navController = findNavController(R.id.nav_host_fragment)
            // val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(post)
            // val fragment = Fragment(R.id.nav_host_fragment)
            // //val navController = findNavController(R.id.nav_host_fragment)
            // findNavController(fragment).navigate(action)
         //}
     }
}
