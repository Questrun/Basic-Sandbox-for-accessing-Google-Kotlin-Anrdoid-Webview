package com.finkq.zetatech.webview

import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(intent.getBooleanExtra("Exit",false)){
            finishAffinity()
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            { finishAndRemoveTask() }
            System.exit(0)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val t1 : TextView=findViewById(R.id.title)
        //val customT: Typeface=Typeface.createFromAsset(assets,"fonts/Pacifico-Regular.ttf")
        //t1.typeface=customT
        val webviewm : WebView=findViewById(R.id.webviewm)
        webviewm.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webviewm.loadUrl("file:///android_asset/basic.html")
        Timer("SettingUp",false).schedule(object: TimerTask(){
            override fun run() {
                val intent = Intent(this@MainActivity, Main2Activity::class.java)
                startActivity(intent)
            }},2500)
        /*
                val sp = getSharedPreferences("firstbase", Context.MODE_PRIVATE)
                val g = sp.getBoolean("firstbase", true)
                /*if (true) {
                    val editor = sp.edit()
                    editor.putBoolean("firstbase", false)
                    editor.apply()
                    val intent = Intent(this@MainActivity, Art1Activity::class.java)
                    startActivity(intent)
                } else {*/
                    val intent = Intent(this@MainActivity, Main2Activity::class.java)
                    startActivity(intent)
//                }
            }
        },  val 2000 : Long, val 2000 :Long)*/

    }
}
