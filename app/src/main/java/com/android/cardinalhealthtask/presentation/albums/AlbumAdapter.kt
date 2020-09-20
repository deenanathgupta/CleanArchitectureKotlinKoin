package com.android.cardinalhealthtask.presentation.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.cardinalhealthtask.domain.model.Album
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.HolderAlbumBinding
import kotlin.properties.Delegates

class AlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var callback: Callback? = null

    fun setListener(callback: Callback) {
        this.callback = callback
    }

    var mAlbumList: List<Album>? by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderAlbumBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_album, parent, false
        )
        return PostViewHolder(holderAlbumBinding)
    }

    override fun getItemCount(): Int {
        return if (mAlbumList.isNullOrEmpty()) 0 else mAlbumList?.size!!
    }

    private fun getItem(position: Int): Album {
        return mAlbumList?.get(position)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    inner class PostViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        init {

        }

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
}