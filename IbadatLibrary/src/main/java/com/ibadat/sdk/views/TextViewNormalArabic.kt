package com.ibadat.sdk.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.Util


internal class TextViewNormalArabic(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {
    init {
        this.typeface = Typeface.createFromFile(
            Util.getUriFromPath(
                context,
                AppConstantUtils.assets + "Arabic.ttf"
            ).path
        )
    }
}