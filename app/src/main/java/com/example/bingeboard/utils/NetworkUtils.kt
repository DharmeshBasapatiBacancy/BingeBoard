package com.example.bingeboard.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


class NetworkUtils {

    companion object{

        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val netInfo = cm!!.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

    }

}