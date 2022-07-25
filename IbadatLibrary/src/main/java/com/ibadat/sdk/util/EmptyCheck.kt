package com.ibadat.sdk.util;

import android.text.TextUtils;

/**
 * Created by user on 10/10/2017.
 */

public class EmptyCheck {

    public static boolean isEmpty5length(CharSequence str) {
        return str == null || str.length() < 5;
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmptyMSISDN(CharSequence mMobileNumberView) {
        return !TextUtils.isEmpty(mMobileNumberView);
    }
}
