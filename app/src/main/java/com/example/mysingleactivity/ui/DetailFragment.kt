package com.example.mysingleactivity.ui

//import android.widget.ShareActionProvider
import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mysingleactivity.R
import com.example.mysingleactivity.databinding.FragmentDetailBinding
import com.example.mysingleactivity.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding = FragmentDetailBinding.bind(view)
        val thumbnailView = binding.imgFrag
        val post = args.post


        binding.tvSubredditF.text = post?.subredditNamePrefixed
        binding.tvTitleFrag.text = post?.title
        binding.tvAuthorFrag.text = post?.author
        val imageUrl = post?.thumbnail
        if (imageUrl != "self") {
            Glide.with(thumbnailView)
                .load(imageUrl)
                .error(R.drawable.baseline_clear_24)
                .into(thumbnailView)
        }else thumbnailView.visibility = GONE
        thumbnailView.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this.context)
            dialogBuilder.setMessage("Do you want to download image?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->

                    if (checkPermission()) {

                        GlobalScope.launch(Dispatchers.IO) {
                            val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                            val uri = Uri.parse(post.thumbnail) // замените URL на нужный вам
                            val request = DownloadManager.Request(uri)
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                            request.setTitle("My File")
                            request.setDescription("Downloading")
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "myfile.jpg") // замените на нужный путь и имя файла

                            val downloadId = downloadManager.enqueue(request)

                        }
                    } else {
                        requestPermission()
                    }
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
            val dialog = dialogBuilder.create()
            dialog.show()
        }

    }

     override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)  {
        super.onCreateOptionsMenu(menu, inflater)
        val post = args.post
         inflater.inflate(R.menu.menu, menu)
         val item = menu.findItem(R.id.menu_item_share)
         val shareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,post.title + "" + post.url)
        shareActionProvider.setShareIntent(intent)
     }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val post = args.post
        when(item.itemId){
            R.id.menu_item_share -> {
                val sendIntent = Intent().apply{
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.title + "" + post.url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent,null)
                startActivity(shareIntent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }


    }


    private fun checkPermission():Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_STORAGE_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                else {
                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment
        private const val REQUEST_STORAGE_PERMISSION = 100
    }
}