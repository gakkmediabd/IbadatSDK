package com.ibadat.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkCheck {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean showNetWorkToast(Context context){
        if (isNetworkAvailable(context)){
            return true;
        }else {
            Toast.makeText(context, "No Internet Connection !!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
