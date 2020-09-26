package com.android.sample.presentation.albums.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.sample.network.NetworkChecker
import com.android.sample.presentation.albums.AlbumViewModel
import com.android.sample.presentation.albums.PhotoAdapter
import com.android.sample.presentation.common.NoInternetLayout
import com.android.sample.utils.DisplayUtility
import com.android.sample.utils.LANDSCAPE_TILE_COUNT
import com.android.sample.utils.PORTRAIT_TILE_COUNT
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.FragmentPhotoBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class PhotoFragment : Fragment(), NoInternetLayout.Callback {
    private lateinit var binding: FragmentPhotoBinding
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private var mAdapter: PhotoAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.title = getString(R.string.photo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val albumId= arguments?.getString(ID)
        setBindingModel()
        doPhotoRequest(albumId)
        val numOfColumns: Int = getColumnCount()
        setAdapter(numOfColumns)
        setObserver()
    }

    private fun setBindingModel() {
        binding.lifecycleOwner = this
        binding.model = albumViewModel
    }

    private fun setAdapter(numOfColumns: Int) {
        mAdapter = PhotoAdapter()
        mAdapter?.setHasStableIds(true)
        binding.photosRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), numOfColumns)
        binding.photosRecyclerView.adapter = mAdapter
    }

    private fun getColumnCount(): Int {
        return if (DisplayUtility.isInLandscapeMode(requireContext())) {
            LANDSCAPE_TILE_COUNT
        } else {
            PORTRAIT_TILE_COUNT
        }
    }

    private fun doPhotoRequest(albumId: String?) {
        if (NetworkChecker(requireContext()).isConnected()) {
            albumViewModel.noInternet.value = false
            albumId?.let { albumViewModel.getPhotos(it) }
        } else {
            albumViewModel.noInternet.value = true
        }
    }

    private fun setObserver() {
        with(albumViewModel) {
            photosData.observe(viewLifecycleOwner, Observer {
                binding.postsProgressBar.visibility = View.GONE
                mAdapter?.mAlbumList = it
            })
            messageData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            })

            showProgressbar.observe(viewLifecycleOwner, Observer { isVisible ->
                binding.postsProgressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
            })
        }
    }

    companion object {
        const val ID = "ALBUM_ID"
    }

    override fun onRetryClick() {
        doPhotoRequest(albumViewModel.clickedAlbum.value?.id.toString())
    }
}