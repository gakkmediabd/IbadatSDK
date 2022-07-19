package com.ibadat.sdk.util;

import android.view.animation.OvershootInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


public class CustomAnimation {


    public static RecyclerView.Adapter getAnimatedRecyclerView(RecyclerView.Adapter adapter){


        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setDuration(400);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());


        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(scaleInAnimationAdapter);
        alphaInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setDuration(400);

        SlideInBottomAnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(alphaInAnimationAdapter);
        return animationAdapter;
    }


}
