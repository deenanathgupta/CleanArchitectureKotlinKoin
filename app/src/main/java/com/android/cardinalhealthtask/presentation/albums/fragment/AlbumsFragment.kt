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
import com.android.cardinalhealthtask.presentation.albums.AlbumAdapter
import com.android.cardinalhealthtask.presentation.albums.AlbumViewModel
import com.android.cardinalhealthtask.extension.toast
import com.android.cardinalhealthtask.network.NetworkChecker
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.FragmentAlbumsBinding
import kotlinx.android.synthetic.main.fragment_albums.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class AlbumsFragment : Fragment(), AlbumAdapter.Callback {
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private var mAdapter: AlbumAdapter? = null
    private var _binding: FragmentAlbumsBinding? = null
    private var mAlbumList = mutableListOf<Album>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        if (NetworkChecker(requireContext()).isConnected()) {
            albumViewModel.getAlbums()
        } else {
            activity?.toast(getString(R.string.no_internet_connection))
        }
        setObserver()
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
            val filteredModelList: List<Album> = mAlbumList.filter {
                it.title.contains(query)
            }
            mAdapter?.setAlbumData(filteredModelList)
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
}