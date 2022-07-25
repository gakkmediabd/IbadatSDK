package com.ibadat.sdk.util

import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

object CustomAnimation {
    fun getAnimatedRecyclerView(adapter: RecyclerView.Adapter<*>?): RecyclerView.Adapter<*> {
        val scaleInAnimationAdapter =
            ScaleInAnimationAdapter(adapter!!)
        scaleInAnimationAdapter.setFirstOnly(false)
        scaleInAnimationAdapter.setDuration(400)
        scaleInAnimationAdapter.setInterpolator(OvershootInterpolator())
        val alphaInAnimationAdapter =
            AlphaInAnimationAdapter(scaleInAnimationAdapter)
        alphaInAnimationAdapter.setFirstOnly(false)
        alphaInAnimationAdapter.setDuration(400)
        return SlideInBottomAnimationAdapter(alphaInAnimationAdapter)
    }
}