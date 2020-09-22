package com.android.cardinalhealthtask.presentation.albums.fragment

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.network.NetworkChecker
import com.android.cardinalhealthtask.presentation.albums.AlbumAdapter
import com.android.cardinalhealthtask.presentation.albums.AlbumViewModel
import com.android.cardinalhealthtask.presentation.common.NoInternetLayout
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.FragmentAlbumsBinding
import kotlinx.android.synthetic.main.fragment_albums.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class AlbumsFragment : Fragment(), AlbumAdapter.Callback, NoInternetLayout.Callback {
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private var mAdapter: AlbumAdapter? = null
    private var binding: FragmentAlbumsBinding? = null
    private var mAlbumList = mutableListOf<Album>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        setClickListener()
        setAdapter()
        doAlbumRequest()
        setObserver()
    }

    private fun setBinding() {
        binding?.lifecycleOwner = this
        binding?.model = albumViewModel
    }

    private fun setClickListener() {
        lyt_no_internet.setCallbackListener(this)
    }

    private fun setAdapter() {
        mAdapter = AlbumAdapter()
        binding?.albumRecyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.albumRecyclerView?.adapter = mAdapter
        mAdapter?.setListener(this)
    }

    private fun doAlbumRequest() {
        if (NetworkChecker(requireContext()).isConnected()) {
            albumViewModel.getAlbums()
            albumViewModel.noInternet.value = false
        } else {
            albumViewModel.noInternet.value = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                filterDataFromAlbumList(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filterDataFromAlbumList(query: String?) {
        query?.let {
            val filteredList: List<Album> = mAlbumList.filter {
                it.title.contains(query)
            }
            mAdapter?.setAlbumData(filteredList)
            albumViewModel.noResultFound.value = filteredList.isEmpty()
        }
    }

    private fun setObserver() {
        with(albumViewModel) {
            albumData.observe(viewLifecycleOwner, Observer {
                album_progress_bar.visibility = GONE
                mAlbumList = it.toMutableList()
                mAdapter?.setAlbumData(it)
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

    override fun onRetryClick() {
        doAlbumRequest()
    }

    override fun onResume() {
        activity?.title = getString(R.string.app_name)
        super.onResume()
    }
}