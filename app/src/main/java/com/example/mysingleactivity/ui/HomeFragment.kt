package com.example.mysingleactivity.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mysingleactivity.adapter.RedditPostAdapter
import com.example.mysingleactivity.databinding.FragmentHomeBinding
import com.example.mysingleactivity.models.Post
import com.example.mysingleactivity.viewmodel.RedditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), RedditPostAdapter.PostClickListener {

    lateinit var navController : NavController
    lateinit var binding: FragmentHomeBinding
    private val viewModel : RedditViewModel by viewModels()
    val adapter = RedditPostAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.redditItemRV?.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getBestPosts().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
    @SuppressLint("ResourceType")
    override fun onPostClick(post: Post) {
        navController = findNavController()
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(post)
        navController.navigate(action)
    }
}