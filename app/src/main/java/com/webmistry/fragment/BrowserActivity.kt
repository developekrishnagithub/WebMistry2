package com.webmistry.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.webmistry.R
import com.webmistry.databinding.BrowserActivityBinding


class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: BrowserActivityBinding
    private var initialLayoutComplete = false
    private lateinit var adSize: AdSize
    private lateinit var adView: AdView
    var webUrl:String?=null
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= BrowserActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState==null){
            val url=intent.getStringExtra(WEB_URL)
            if (url != null) {
                binding.webView.loadUrl(url)
            }
        }else{
           val containUrl=savedInstanceState.containsKey(WEB_URL)
            if (containUrl){
                webUrl=savedInstanceState.getString(WEB_URL)
                binding.webView.loadUrl(webUrl!!)
            }
        }
        binding.apply {
            webView.apply {
                settings.javaScriptEnabled=true
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        webUrl=url
                        binding.linearProgressIndicator.visibility = View.GONE
                        linearProgressIndicator.isIndeterminate = false
                        binding.linearProgressIndicator.progress = progress
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        webUrl=url
                        binding.linearProgressIndicator.visibility = View.VISIBLE
                        linearProgressIndicator.isIndeterminate = true
                        linearProgressIndicator.setProgress(progress,true)
                    }

                }
            }
        }
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                  finish()
                }
            }
        }

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = binding.adContainerInBrowserActivity.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        val adWidth = (adWidthPixels / density).toInt()
        adView=AdView(this)
        binding.adContainerInBrowserActivity.addView(adView)
        adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        binding.adContainerInBrowserActivity.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
    }
    private fun loadBanner() {
        adView.adUnitId = resources.getString(R.string.banner_id)
        adView.setAdSize(adSize)
        val adRequest = AdRequest
            .Builder()
            .build()
        adView.loadAd(adRequest)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (webUrl!=null){
            outState.putString(WEB_URL,webUrl)
        }
    }


}