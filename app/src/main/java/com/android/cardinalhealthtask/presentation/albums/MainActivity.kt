package com.android.cardinalhealthtask.presentation.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.presentation.albums.AlbumViewModel
import com.android.cardinalhealthtask.presentation.albums.AlbumAdapter
import com.android.cardinalhealthtask.utils.isNetworkAvailable
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AlbumAdapter.Callback {
    private lateinit var activityPostsBinding: ActivityMainBinding
    private val albumViewModel: AlbumViewModel by viewModel()
    private var mAdapter: AlbumAdapter? = null

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mAdapter = AlbumAdapter()
        activityPostsBinding.albumRecyclerView.layoutManager = GridLayoutManager(this, 2)
        activityPostsBinding.albumRecyclerView.adapter = mAdapter
        mAdapter?.setListener(this)
        if(isNetworkAvailable()) {
            albumViewModel.getAlbums()
        } else {
            Toast.makeText(
                this,
                getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
        }
        with(albumViewModel) {
            albumData.observe(this@MainActivity, Observer {
                activityPostsBinding.postsProgressBar.visibility = GONE
                mAdapter?.mAlbumList = it
            })
            messageData.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, it, LENGTH_LONG).show()
            })

            showProgressbar.observe(this@MainActivity, Observer { isVisible ->
                posts_progress_bar.visibility = if (isVisible) VISIBLE else GONE
            })

            photosData.observe(this@MainActivity, Observer {

            })
        }
    }

    override fun onItemClick(album: Album) {
        PhotosActivity.start(this, album)
    }
}