package com.android.sample.presentation.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.domain.model.AlbumItem
import com.cardinalhealth.sample.R
import com.cardinalhealth.sample.databinding.HolderPhotoBinding
import kotlin.properties.Delegates

class PhotoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mAlbumList: List<AlbumItem>? by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return mAlbumList?.get(position)?.id?.toLong() ?: 0.toLong()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderAlbumBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_photo, parent, false
        )
        return PostViewHolder(holderAlbumBinding)
    }

    override fun getItemCount(): Int {
        return if (mAlbumList.isNullOrEmpty()) 0 else mAlbumList?.size!!
    }

    private fun getItem(position: Int): AlbumItem {
        return mAlbumList?.get(position)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    inner class PostViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(post: AlbumItem) {
            (viewDataBinding as HolderPhotoBinding).album = post
        }
    }

    companion object {
        private val TAG = PhotoAdapter::class.java.simpleName
    }

}