package com.android.sample.presentation.albums

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.sample.presentation.albums.fragment.PhotoFragment.Companion.ID
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var activityPostsBinding: ActivityMainBinding
    private val albumViewModel: AlbumViewModel by viewModel()
    private lateinit var navController: NavController

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if(navController.currentDestination?.id == R.id.photoFragment) {
            navController.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}