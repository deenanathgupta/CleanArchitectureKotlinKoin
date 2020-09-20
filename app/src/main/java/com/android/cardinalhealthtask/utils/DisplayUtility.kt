package com.android.cardinalhealthtask.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point

object DisplayUtility {
    fun getScreenWidth(context: Context): Int {
        val size = Point()
        (context as Activity).windowManager.defaultDisplay.getSize(size)
        return size.x
    }

    fun getScreenHeight(context: Context): Int {
        val size = Point()
        (context as Activity).windowManager.defaultDisplay.getSize(size)
        return size.y
    }

    fun isInLandscapeMode(context: Context): Boolean {
        var isLandscape = false
        if (context.resources
                .configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        ) {
            isLandscape = true
        }
        return isLandscape
    }
}