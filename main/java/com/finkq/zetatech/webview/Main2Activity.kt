package com.finkq.zetatech.webview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.DialogInterface
import android.content.Intent
import android.net.Network
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.content.IntentFilter
import android.os.Handler


class Main2Activity : AppCompatActivity() {
  private var doubleBackToExitPressedOnce=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val filter = IntentFilter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            createChangeConnectivityMonitor()
            filter.addAction(ConnectivityManager.EXTRA_NO_CONNECTIVITY)
        } else {
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        }
        registerReceiver(SyncOnConnectivityReceiver(), filter)
        //LocaleUpdate(applicationContext)
        val webview1 : WebView =findViewById(R.id.webview1)
        webview1.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (!(activeNetworkInfo != null && activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected)) {
            webview1.loadUrl("file:///android_asset/internet.html")
        }
        else{
            webview1.loadUrl("https://www.google.com    ")
        }

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val adB = AlertDialog.Builder(this)
        adB.setMessage("Exit The App?").setCancelable(true).setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which ->
                val c = applicationContext
                val i = Intent(c, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                i.putExtra("Exit", true)
                startActivity(i)
                finishAffinity()
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                {
                    finishAndRemoveTask()
                }
            })
        adB.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
        val aD = adB.create()
        aD.show()
/*        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }*/

        }
        val webview2 : WebView =findViewById(R.id.webview1)
        if(webview2.canGoBack())
        {
            webview2.goBack()
        }
        else {
            this.doubleBackToExitPressedOnce = true
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 1000)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun createChangeConnectivityMonitor() {

        val intent = Intent(ConnectivityManager.EXTRA_NO_CONNECTIVITY)
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                /**
                 * @param network
                 */
                override fun onAvailable(network: Network) {
                    sendBroadcast(intent)
                }

                /**
                 * @param network
                 */
                override fun onLost(network: Network) {
                    sendBroadcast(intent)
                }
            })

    }
}
