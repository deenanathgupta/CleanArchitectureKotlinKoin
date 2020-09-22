package com.android.cardinalhealthtask.presentation.albums.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.presentation.albums.AlbumAdapter
import com.android.cardinalhealthtask.presentation.albums.AlbumViewModel
import com.android.cardinalhealthtask.utils.isNetworkAvailable
import com.android.cardinalhealthtask.utils.toast
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.FragmentAlbumsBinding
import kotlinx.android.synthetic.main.fragment_albums.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class AlbumsFragment : Fragment(), AlbumAdapter.Callback {
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private var mAdapter: AlbumAdapter? = null
    private var _binding: FragmentAlbumsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = AlbumAdapter()
        _binding?.albumRecyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        _binding?.albumRecyclerView?.adapter = mAdapter
        mAdapter?.setListener(this)
        if (isNetworkAvailable()) {
            albumViewModel.getAlbums()
        } else {
            activity?.toast(getString(R.string.no_internet_connection))
        }
        setObserver()
    }

    private fun setObserver() {
        with(albumViewModel) {
            albumData.observe(viewLifecycleOwner, Observer {
                album_progress_bar.visibility = GONE
                mAdapter?.mAlbumList = it
            })
            messageData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, LENGTH_LONG).show()
            })

            showProgressbar.observe(viewLifecycleOwner, Observer { isVisible ->
                album_progress_bar.visibility = if (isVisible) VISIBLE else GONE
            })

            photosData.observe(viewLifecycleOwner, Observer {

            })
        }
    }

    override fun onItemClick(album: Album) {
        albumViewModel.clickedAlbum.value = album
    }
}