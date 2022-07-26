package com.ibadat.sdk.activities


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseActivity

internal class YoutubePlayerActivity : BaseActivity() {
    private lateinit var wvYoutubePlayer: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_youtube_player)
        wvYoutubePlayer = findViewById(R.id.wv_youtube_player)
        val videoId = intent.getStringExtra("id")

        wvYoutubePlayer.webViewClient = WebViewClient()
        wvYoutubePlayer.loadUrl("https://www.youtube.com/embed/" + videoId + "?modestbranding=1&amp;autoplay=1&amp;controls=0&amp;showinfo=0&amp;rel=0&amp;enablejsapi=1&amp;version=3&amp;playerapiid=iframe_YTP_1525952728130&amp;origin=http://mygp.ibadat.co&amp;allowfullscreen=true&amp;wmode=transparent&amp;iv_load_policy=3&amp;playsinline=0&amp;html5=1&amp;widgetid=" + videoId)
        wvYoutubePlayer.settings.javaScriptEnabled = true
        wvYoutubePlayer.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (wvYoutubePlayer.canGoBack())
            wvYoutubePlayer.goBack()
        else
            super.onBackPressed()
    }
}

