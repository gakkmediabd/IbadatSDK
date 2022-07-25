package com.ibadat.sdk.views

import android.content.Context
import com.ibadat.sdk.util.Util.getUriFromPath
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ibadat.sdk.util.AppConstantUtils

class MyCustomTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(
    context!!, attrs
) {
    init {
        this.typeface = Typeface.createFromFile(
            getUriFromPath(
                context!!,
                AppConstantUtils.assets + "NotoSansBengaliUI-Regular.ttf"
            ).path
        )
    }
}