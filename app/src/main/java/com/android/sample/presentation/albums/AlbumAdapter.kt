package com.android.sample.presentation.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.domain.model.Album
import com.android.sample.presentation.base.BaseDiff
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.HolderAlbumBinding

class AlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var callback: Callback? = null
    var mAlbumList:  MutableList<Album> = mutableListOf()

    fun setListener(callback: Callback) {
        this.callback = callback
    }

    fun setAlbumData(mAlbumList: List<Album>) {
        val albumDiff =
            SuggestionsDiff(this.mAlbumList, mAlbumList)
        val diffResults = DiffUtil.calculateDiff(albumDiff)
        this.mAlbumList.clear()
        this.mAlbumList.addAll(mAlbumList)
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderAlbumBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_album, parent, false
        )
        return PostViewHolder(holderAlbumBinding)
    }

    override fun getItemCount(): Int {
        return if (mAlbumList.isNullOrEmpty()) 0 else mAlbumList.size
    }

    private fun getItem(position: Int): Album {
        return mAlbumList[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    inner class PostViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(post: Album) {
            (viewDataBinding as HolderAlbumBinding).album = post
            viewDataBinding.root.setOnClickListener {
                callback?.onItemClick(post)
            }
        }
    }

    companion object {
        private val TAG = AlbumAdapter::class.java.simpleName
    }

    interface Callback {
        fun onItemClick(album: Album)
    }

    inner class SuggestionsDiff(
        private val oldList: List<Album>,
        private val newList: List<Album>
    ) : BaseDiff<Album>(oldList, newList) {

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition].id == newList[newPosition].id
        }
    }
}