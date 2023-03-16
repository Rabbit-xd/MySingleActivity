package com.example.mysingleactivity.adapter
/**
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysingleactivity.R
import com.example.mysingleactivity.databinding.ItemDataModelBinding
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.utils.DiffUtilCallBack

class PostAdapter(private val onItemClick: (Post)-> Unit) : //onClickListener: OnClickListener) :
    PagingDataAdapter<Post, PostAdapter.PostViewHolder> (DiffUtilCallBack())  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_model,parent,false)
        val binding = ItemDataModelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        post?.let { holder.bind(it,onItemClick) }
    }


//    class PostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    class PostViewHolder(
    private val binding: ItemDataModelBinding) :
    RecyclerView.ViewHolder(binding.root) {
        //binding = ItemDataModelBinding.bind(binding)
      //  private val scoreText = binding.tvScore
      //  private val commentText= binding.tvComments
        private val titleText = binding.tvTitle
        private val subText = binding.tvSubreddit
        private val authorText = binding.tvAuthor
        private var thumbnailView = binding.imageView
        fun bind (post: Post, onItemClick: (Post) -> Unit){
            with(post) {
                itemView.setOnClickListener {
                    onItemClick(post)
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
                    thumbnailView.visibility = GONE
                }

            }

        }

    }


    interface OnClickListener{
        fun onClick(post: Post)
    }

}
        **/