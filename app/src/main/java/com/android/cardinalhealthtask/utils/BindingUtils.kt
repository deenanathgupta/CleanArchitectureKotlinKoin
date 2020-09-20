package com.android.cardinalhealthtask.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.cardinalhealth.sample.R
import com.squareup.picasso.Picasso

object BindingUtils {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            val numOfColumns: Int = if (DisplayUtility.isInLandscapeMode(view.context)) { 4 } else { 3 }
            val width = DisplayUtility.getScreenWidth(view.context)
            val dimension = width / numOfColumns
            Picasso.get().load(url)
                .error(R.drawable.ic_img_placeholder).resize(dimension, dimension).into(view)
        }
    }
}