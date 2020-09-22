package com.android.cardinalhealthtask.presentation.albums.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cardinalhealthtask.extension.toast
import com.android.cardinalhealthtask.network.NetworkChecker
import com.android.cardinalhealthtask.presentation.albums.AlbumViewModel
import com.android.cardinalhealthtask.presentation.albums.PhotoAdapter
import com.android.cardinalhealthtask.utils.*
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.FragmentPhotoBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class PhotoFragment : Fragment() {
    private lateinit var _binding: FragmentPhotoBinding
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
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val albumId= arguments?.getString(ID)
        doPhotoRequest(albumId)
        val numOfColumns: Int = getColumnCount()
        setAdapter(numOfColumns)
        setObserver()
    }

    private fun setAdapter(numOfColumns: Int) {
        mAdapter = PhotoAdapter()
        _binding.photosRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), numOfColumns)
        _binding.photosRecyclerView.adapter = mAdapter
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
            albumId?.let { albumViewModel.getPhotos(it) }
        } else {
            activity?.toast(getString(R.string.no_internet_connection))
        }
    }

    private fun setObserver() {
        with(albumViewModel) {
            photosData.observe(viewLifecycleOwner, Observer {
                _binding.postsProgressBar.visibility = View.GONE
                mAdapter?.mAlbumList = it
            })
            messageData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            })

            showProgressbar.observe(viewLifecycleOwner, Observer { isVisible ->
                _binding.postsProgressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
            })
        }
    }

    companion object {
        const val ID = "ALBUM_ID"
    }
}