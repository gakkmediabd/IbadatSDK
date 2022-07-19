package com.ibadat.sdk.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ibadat.sdk.R;




public class CustomDialog {
    private static ProgressDialog pdialog;
    private static final int STORAGE_PERMISSION_CODE = 101;

    public static void showDownloadConfirmationDialog(final Context context, final String ContentName, final String physicalUrl, final String requestType) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_confirmation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button dialogBtn_cancel = dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(v -> dialog.cancel());

        Button dialogBtn_okay = dialog.findViewById(R.id.btn_Confirmation);
        dialogBtn_okay.setOnClickListener(v -> {
            if (isReadStorageAllowed(context)) {
                DownloadImage.StartDownload(context, ContentName, physicalUrl, requestType);
            }
            dialog.dismiss();
        });
        dialog.show();
    }



    private static boolean isReadStorageAllowed(Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions((Activity) context, new String[] { android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
        else {
            return true;
        }
        return false;
    }
}
