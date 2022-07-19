package com.ibadat.sdk.activities


import android.os.Bundle
import android.view.Window
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseActivity
import com.ibadat.sdk.databinding.ActivityYoutubePlayerBinding

class YoutubePlayerActivity : BaseActivity() {
    private lateinit var binding: ActivityYoutubePlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_youtube_player)

        setContentView(binding.root)
        val videoId = intent.getStringExtra("id")

        binding.youtubePlayer.webViewClient = WebViewClient()
        binding.youtubePlayer.loadUrl("https://www.youtube.com/embed/" + videoId + "?modestbranding=1&amp;autoplay=1&amp;controls=0&amp;showinfo=0&amp;rel=0&amp;enablejsapi=1&amp;version=3&amp;playerapiid=iframe_YTP_1525952728130&amp;origin=http://mygp.ibadat.co&amp;allowfullscreen=true&amp;wmode=transparent&amp;iv_load_policy=3&amp;playsinline=0&amp;html5=1&amp;widgetid=" + videoId)
        binding.youtubePlayer.settings.javaScriptEnabled = true
        binding.youtubePlayer.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (binding.youtubePlayer.canGoBack())
            binding.youtubePlayer.goBack()
        else
            super.onBackPressed()
    }
}

