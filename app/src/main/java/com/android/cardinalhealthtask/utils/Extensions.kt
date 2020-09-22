package com.android.cardinalhealthtask.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.isNetworkAvailable(): Boolean {
    val connectivityManager =
        activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text.orEmpty(), duration).show()
