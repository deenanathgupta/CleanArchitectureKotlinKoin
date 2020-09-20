package com.android.cardinalhealthtask.presentation.albums

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.utils.DisplayUtility
import com.android.cardinalhealthtask.utils.isNetworkAvailable
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.ActivityPhotosBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PhotosActivity : AppCompatActivity() {
    private lateinit var activityPostsBinding: ActivityPhotosBinding
    private val albumViewModel: AlbumViewModel by viewModel()
    private var mAdapter: PhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_photos)
        val numOfColumns: Int = if (DisplayUtility.isInLandscapeMode(this)) {
            4
        } else {
            3
        }

        mAdapter = PhotoAdapter()
        activityPostsBinding.photosRecyclerView.layoutManager = GridLayoutManager(this, numOfColumns)
        activityPostsBinding.photosRecyclerView.adapter = mAdapter
        if(isNetworkAvailable()) {
            val id = intent.getIntExtra(ID, 0).toString()
            albumViewModel.getPhotos(id)
        } else {
            Toast.makeText(
                this,
                getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
        }
        with(albumViewModel) {
            photosData.observe(this@PhotosActivity, Observer {
                activityPostsBinding.postsProgressBar.visibility = View.GONE
                mAdapter?.mAlbumList = it
            })
            messageData.observe(this@PhotosActivity, Observer {
                Toast.makeText(this@PhotosActivity, it, Toast.LENGTH_LONG).show()
            })

            showProgressbar.observe(this@PhotosActivity, Observer { isVisible ->
                activityPostsBinding.postsProgressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
            })
        }
    }

    companion object {
        const val ID = "Id"
        fun start(context: Context, album: Album) {
            val intent = Intent(context, PhotosActivity::class.java)
            intent.putExtra(ID, album.id)
            context.startActivity(intent)
        }
    }
}