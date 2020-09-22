package com.android.cardinalhealthtask.presentation.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.presentation.albums.AlbumViewModel
import com.android.cardinalhealthtask.presentation.albums.AlbumAdapter
import com.android.cardinalhealthtask.presentation.albums.fragment.PhotoFragment.Companion.ID
import com.android.cardinalhealthtask.utils.isNetworkAvailable
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityPostsBinding: ActivityMainBinding
    private val albumViewModel: AlbumViewModel by viewModel()
    lateinit var navController: NavController

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavigation()
        setObserver()
    }

    private fun setObserver() {
        with(albumViewModel) {
            clickedAlbum.observe(this@MainActivity, Observer {
                val bundle = bundleOf(ID to it.id.toString())
                navController.navigate(R.id.action_photoFragment, bundle)
            })
        }
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
    }
}