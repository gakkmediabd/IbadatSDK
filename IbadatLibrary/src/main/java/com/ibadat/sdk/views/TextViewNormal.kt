package com.ibadat.sdk.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.Util.getUriFromPath

internal class TextViewNormal(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {
    init {
        this.typeface = Typeface.createFromFile(
            getUriFromPath(
                context,
                AppConstantUtils.assets + "Roboto_regular.ttf"
            ).path
        )
    }
}