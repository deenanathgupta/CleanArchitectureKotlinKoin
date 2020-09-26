package com.android.sample.utils

import android.view.View

object AppUtils {
    object Utils {
        fun getDimension(view: View): Int {
            val numOfColumns: Int = if (DisplayUtility.isInLandscapeMode(view.context)) {
                LANDSCAPE_TILE_COUNT
            } else {
                PORTRAIT_TILE_COUNT
            }
            val width = DisplayUtility.getScreenWidth(view.context)
            return width / numOfColumns
        }
    }
}