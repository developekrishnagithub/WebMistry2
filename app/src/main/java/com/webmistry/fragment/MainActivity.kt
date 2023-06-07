package com.webmistry.fragment

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.webmistry.R
import com.webmistry.callback.ItemClick
import com.webmistry.database.Browser
import com.webmistry.databinding.MainActivityBinding
import com.webmistry.recyclerview.WebAdapter
import org.jetbrains.annotations.TestOnly

const val WEB_ICON = "web_icon"
const val WEB_NAME = "web_NAME"
const val WEB_URL = "web_URL"
const val TAB_DIALOG_KEY = "TAB_DIALOG_KEY"


class MainActivity : AppCompatActivity(), ItemClick, OnUserEarnedRewardListener {

    private lateinit var binding: MainActivityBinding
    private var initialLayoutComplete = false
    private lateinit var adSize: AdSize
    private lateinit var adView: AdView
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.includeMainToolbar.mainToolbar)
        MobileAds.initialize(this) {
            loadRewardedAds()
        }
        val display = this.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = binding.adsContainer.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        val adWidth = (adWidthPixels / density).toInt()
        adView = AdView(this)
        binding.adsContainer.addView(adView)
        adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        binding.adsContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
        val data = getListOfSearchEngine()
        val adapter = WebAdapter(data, this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)
        val onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (rewardedInterstitialAd != null) {
                    rewardedInterstitialAd?.show(this@MainActivity, this@MainActivity)
                } else {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(onBackPressed)
    }

    private fun getListOfSearchEngine(): ArrayList<Browser> {
        val arrayList = ArrayList<Browser>()
        arrayList.apply {
            add(Browser(R.drawable.google, "Google", "https://www.google.com"))
            add(Browser(R.drawable.bing, "Microsoft Bing", "https://www.bing.com"))
            add(Browser(R.drawable.yahoo, "Yahoo!", "https://www.yahoo.com"))
            add(Browser(R.drawable.yandex, "Yandex", "https://www.yandex.com"))
            add(Browser(R.drawable.duckduckgo, "DuckDuckGo", "https://www.duckduckgo.com"))
            add(Browser(R.drawable.ask, "Ask.com", "https://www.ask.com"))
            add(Browser(R.drawable.ecosia, "ecosia", "https://www.ecosia.com"))
            add(Browser(R.drawable.opera_mini, "Opera Mini", "https://opera.com"))
            add(Browser(R.drawable.startpage, "Start Page", "https://startpage.com"))
            add(Browser(R.drawable.brave, "Brave", "https://search.brave.com/"))
        }
        return arrayList
    }

    override fun ItemClick(browser: Browser) {
        val intent = Intent(this@MainActivity, BrowserActivity::class.java)
        intent.apply {
            putExtra(WEB_NAME, browser.webName)
            putExtra(WEB_ICON, browser.webImage)
            putExtra(WEB_URL, browser.url)
        }
        startActivity(intent)
    }

    private fun loadBanner() {
        adView.adUnitId = resources.getString(R.string.banner_id)

        adView.setAdSize(adSize)
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this device."
        val adRequest = AdRequest
            .Builder()
            .build()
        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    private fun loadRewardedAds() {
//        "ca-app-pub-3940256099942544/5354046379"
        RewardedInterstitialAd.load(this, resources.getString(R.string.reword_id),
            AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    Log.d("AdminTag", "Rewarded Ad was loaded.")
                    rewardedInterstitialAd = ad
                    rewardedInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                finish()
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                super.onAdFailedToShowFullScreenContent(p0)
                                Log.d("AdminTag", "onAdFailedToShowFullScreenContent")
                                finish()
                            }

                            override fun onAdClicked() {
                                super.onAdClicked()
                                Log.d("AdminTag", "onAdClicked")
                            }

                            override fun onAdImpression() {
                                super.onAdImpression()
                                rewardedInterstitialAd = null
                                Log.d("AdminTag", "onAdImpression")
                            }

                            override fun onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent()
                                Log.d("AdminTag", "onAdShowedFullScreenContent")
                            }
                        }
                }
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("AdminTag", "on Ads:${adError.message}")
                    rewardedInterstitialAd = null
                }
            })

    }

    override fun onUserEarnedReward(p0: RewardItem) {
        Toast.makeText(this, "You earn ${p0.amount} points", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareApp -> {
//                val intent=Intent(Intent.ACTION_SEND)
//                intent.apply {
//                    type="text/plain"
//                    putExtra(Intent.EXTRA_SUBJECT,"My subject")
//                    putExtra(Intent.EXTRA_TEXT,"Enjoy Top Browser in one app share your friends and make save time and your memory storage ${}")
//                    startActivity(Intent.createChooser(intent,null))
//                }
            }

            R.id.addNewTab -> {
                Toast.makeText(this, "This Service under progress", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu,menu)
//        return true
//    }
}
