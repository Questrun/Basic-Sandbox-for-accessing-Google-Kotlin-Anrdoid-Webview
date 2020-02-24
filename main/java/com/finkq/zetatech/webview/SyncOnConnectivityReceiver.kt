package com.finkq.zetatech.webview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.ConnectivityManager
import java.util.concurrent.Executors


class SyncOnConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (context != null && isNetworkAvailable(context)) {
            Executors.newSingleThreadExecutor().submit(Runnable { syncBusData(context) })
        }
    }
    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }

    private fun syncBusData(context: Context) {

    }
}
