package com.android.cardinalhealthtask.network

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.android.cardinalhealthtask.extension.connectivityManager

class NetworkChecker(private val context: Context) : INetworkChecker {

    override fun isConnected() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        postMarshmallowInternetCheck(context.connectivityManager)
    } else {
        preMarshmallowInternetCheck(context.connectivityManager)
    }

    private fun preMarshmallowInternetCheck(connectivityManager: ConnectivityManager?): Boolean {
        val activeNetwork = connectivityManager?.activeNetworkInfo
        activeNetwork?.let {
            return it.isConnected
        }
        return false
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun postMarshmallowInternetCheck(connectivityManager: ConnectivityManager?): Boolean {
        val connection = connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
        return isNetworkAvailable(connection)
    }

    private fun isNetworkAvailable(connection: NetworkCapabilities?) =
        connection != null && (connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))

}

