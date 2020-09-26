package com.android.sample.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.sample.utils.AppUtils.Utils.getDimension
import com.cardinalhealth.sample.R
import com.squareup.picasso.Picasso

object BindingUtils {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            val dimension = getDimension(view)
            Picasso.get().load(url)
                .error(R.drawable.ic_img_placeholder).resize(dimension, dimension).into(view)
        }
    }
}