package com.ibadat.sdk.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.ibadat.sdk.util.AppConstantUtils;
import com.ibadat.sdk.util.Util;


public class MyCustomTextView extends AppCompatTextView {
    public MyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "NotoSansBengaliUI-Regular.ttf"));
        this.setTypeface(Typeface.createFromFile(Util.INSTANCE.getUriFromPath(context, AppConstantUtils.assets + "NotoSansBengaliUI-Regular.ttf").getPath()));
    }
}
