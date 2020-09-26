package com.android.sample.extension

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

val Context.connectivityManager: ConnectivityManager?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text.orEmpty(), duration).show()