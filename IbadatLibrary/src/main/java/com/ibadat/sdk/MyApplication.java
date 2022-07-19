package com.ibadat.sdk;

import static com.ibadat.sdk.util.ConstantsKt.*;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;

public class MyApplication extends Application {
    public static int width=0;
    public static int height=0;

    public static final String TAG = MyApplication.class
            .getSimpleName();

    private static MyApplication mInstance;
    public static volatile Context applicationContext = null;
    public static volatile Handler applicationHandler = null;

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
        //CommonAll.changeLocale(this);
        mInstance = this;
        applicationContext  = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
        context = getApplicationContext();

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    private void createNotificationChannels() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    PLAYER_NOTIFICATION_CHANNEL_ID,
                    PLAYER_NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription( "This notification use for show ibadat player");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}
