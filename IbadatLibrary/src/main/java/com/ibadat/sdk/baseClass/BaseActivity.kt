package com.ibadat.sdk.baseClass

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {
    companion object {
        lateinit var alertDialog: AlertDialog
    }
}