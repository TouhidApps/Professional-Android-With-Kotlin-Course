package com.touhidapps.myfirstwebview


import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.touhidapps.myfirstwebview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webView.webViewClient = MyWebViewClient(binding)
        binding.webView.webChromeClient = MyWebChromeClient(binding)
        binding.webView.loadUrl("https://touhidapps.com")
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = true

        listeners()

    } // onCreate

    private fun listeners() {

        binding.btnBack.setOnClickListener {
            if (!isOnline(this)) {
                Toast.makeText(this, "No internet available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }
        }

        binding.btnForward.setOnClickListener {
            if (!isOnline(this)) {
                Toast.makeText(this, "No internet available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.webView.canGoForward()) {
                binding.webView.goForward()
            }
        }

        binding.btnGoogle.setOnClickListener {
            if (!isOnline(this)) {
                Toast.makeText(this, "No internet available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.webView.loadUrl("https://googl_e.com")
        }

        binding.btnTouhidApps.setOnClickListener {
            if (!isOnline(this)) {
                Toast.makeText(this, "No internet available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.webView.loadUrl("https://touhidapps.com")
        }

    } // listeners

    private fun isOnline(context: Context): Boolean {

        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected

    }

    class MyWebViewClient(var binding: ActivityMainBinding): WebViewClient() {
        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)

            view?.loadUrl("file:///android_asset/web/index.html")

            // Your code
            Log.d("WebViewClient: ", "Error: ${error?.description}")

        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressBarLinear.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBarLinear.visibility = View.GONE
        }

    }

    class MyWebChromeClient(var binding: ActivityMainBinding): WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            binding.progressBarLinear.progress = newProgress
            Log.d("WebViewClient: ", "Progress: ${newProgress}")
        }

    }

}