package com.ibadat.gpsdk

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ibadat.sdk.IbadatSdkCore


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 //        AppPreference.init(this)
        val button = findViewById<Button>(R.id.btnSurahlist)
        val btnDua = findViewById<Button>(R.id.btn_dua)
        val btnHadith = findViewById<Button>(R.id.btn_hadith)
        val btnTasbih = findViewById<Button>(R.id.btn_tasbih)
        val btnIfter = findViewById<Button>(R.id.btn_iftar)
        val btnPrayerTime = findViewById<Button>(R.id.btn_namaz_time)
        val btnIslamicCalendar = findViewById<Button>(R.id.btn_islamic_calender)
        val btnCompass = findViewById<Button>(R.id.btn_compass)
        val btnsalat = findViewById<Button>(R.id.btn_salat)
        val btnzakat = findViewById<Button>(R.id.btn_zakat)
        val btnliveVideo = findViewById<Button>(R.id.btn_liveVideo)
        val btnmosque = findViewById<Button>(R.id.btn_mosqueLocator)
        val btnwallpaper = findViewById<Button>(R.id.btn_wallpaper)

        btnDua.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.DUA)
        }
        btnHadith.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.HADITH)
        }
        button.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.SURAH)
        }
        btnTasbih.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.TASBIH)
        }
        btnIfter.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.ROJA)
        }
        btnIslamicCalendar.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.ISLAMICCALANDER)
        }
        btnCompass.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.COMPASS)
        }
        btnsalat.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.SALATLEARNING)
        }
        btnzakat.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.ZAKAT)
        }
        btnliveVideo.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.LIVEVIDEO)
        }
        btnmosque.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.NEAREST_MOSQUE)
        }
        btnwallpaper.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.WALLPAPER)
        }
        btnPrayerTime.setOnClickListener {
            IbadatSdkCore.openFeature(this, IbadatSdkCore.PAYER_TIME)
        }
    }
}