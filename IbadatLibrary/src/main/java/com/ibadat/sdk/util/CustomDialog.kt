package com.ibadat.sdk.util

import android.Manifest
import com.ibadat.sdk.R
import android.graphics.drawable.ColorDrawable
import android.content.pm.PackageManager
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object CustomDialog {
    private val pdialog: ProgressDialog? = null
    private const val STORAGE_PERMISSION_CODE = 101
    fun showDownloadConfirmationDialog(
        context: Context,
        ContentName: String,
        physicalUrl: String,
        requestType: String
    ) {
        val dialog = Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_confirmation)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogBtnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        dialogBtnCancel.setOnClickListener { v: View? -> dialog.cancel() }
        val dialogBtnOkay = dialog.findViewById<Button>(R.id.btn_Confirmation)
        dialogBtnOkay.setOnClickListener { v: View? ->
            if (isReadStorageAllowed(context)) {
                DownloadImage.startDownload(context, ContentName, physicalUrl, requestType)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun isReadStorageAllowed(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        } else {
            return true
        }
        return false
    }
}