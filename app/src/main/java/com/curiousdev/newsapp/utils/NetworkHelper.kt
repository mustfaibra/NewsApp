package com.curiousdev.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.net.ConnectivityManagerCompat

object NetworkHelper {

    public fun checkIfConnected(context: Context)  : Boolean {
        val connectivityMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityMgr.activeNetworkInfo
        return networkInfo?.isConnected?:false

    }
}