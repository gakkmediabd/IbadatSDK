package com.ibadat.sdk.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.NavigationRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ibadat.sdk.IbadatSdkCore
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseActivity
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.restrepo.NetworkDataCallBack
import com.ibadat.sdk.player.PlayerService
import com.ibadat.sdk.player.PlayerViewModel
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.FileHandler
import com.ibadat.sdk.util.IbadatSDK_Resource_Url
import com.ibadat.sdk.util.LocationFinder


internal class HomeIbadatSdkActivity : BaseActivity(), NetworkDataCallBack {
    companion object {
        internal var backPressCount = 0
    }

    private var globalDataExist = false
    private var dataShowType = 0
    private lateinit var toolbar: Toolbar
    private lateinit var actionBar: ActionBar
    private lateinit var pbActivityLoader: ProgressBar
    private lateinit var navController: NavController
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_ibadat_sdk)
        iniUI()
        AppPreference.initSharedPreferences(this)
        val countryName = LocationFinder.getLocationName(this)

        val intent = intent
        dataShowType = intent.getIntExtra(AppConstantUtils.requestType, 0)
        setupPlayerViewModel()
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        if (!AppPreference.isSDkResourceDownloadStatus && !AppPreference.isSDkResourceExtractStatus) {
            Toast.makeText(this, "Wait for download resource", Toast.LENGTH_LONG).show()
            FileHandler.DownloadFileFromUrl(
                this,
                IbadatSDK_Resource_Url,
                this
            ).execute()
        } else {
            eventOnUI(dataShowType)
        }
    }

    private fun eventOnUI(dataShowType: Int) {
        when (dataShowType) {
            IbadatSdkCore.DUA -> {
                setupNavGraph(R.navigation.navgraph_dua_show)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.DUA
            }
            IbadatSdkCore.HADITH -> {
                setupNavGraph(R.navigation.navgraph_dua_show)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.HADITH
            }
            IbadatSdkCore.SURAH -> {
                setupNavGraph(R.navigation.navgraph)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.SURAH
            }
            IbadatSdkCore.ROJA -> {
                AppPreference.language = "bn"
                setupNavGraph(R.navigation.navgraph_roja)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.PAYER_TIME
            }
            IbadatSdkCore.PAYER_TIME -> {
                setupNavGraph(R.navigation.navgraph_prayertime)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.PAYER_TIME
            }
            IbadatSdkCore.TASBIH -> {
                setupNavGraph(R.navigation.navgraph_tasbih_show)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.TASBIH
            }
            IbadatSdkCore.ISLAMICCALANDER -> {
                setupNavGraph(R.navigation.navgraph_islamic_calender)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.ISLAMICCALANDER
            }
            IbadatSdkCore.COMPASS -> {
                setupNavGraph(R.navigation.navgraph_compass)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.COMPASS
            }
            IbadatSdkCore.SALATLEARNING -> {
                setupNavGraph(R.navigation.navgraph_salat_learning)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.SALATLEARNING
            }
            IbadatSdkCore.ZAKAT -> {
                setupNavGraph(R.navigation.navgraph_jakat)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.ZAKAT
            }
            IbadatSdkCore.LIVEVIDEO -> {
                setupNavGraph(R.navigation.navgraph_live_video)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.LIVEVIDEO
            }
            IbadatSdkCore.NEAREST_MOSQUE -> {
                setupNavGraph(R.navigation.navgraph_nearest_mosque)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.NEAREST_MOSQUE
            }
            IbadatSdkCore.WALLPAPER -> {
                setupNavGraph(R.navigation.navgraph_wall_paper)
                AppConstantUtils.requestTypeValue = IbadatSdkCore.WALLPAPER
            }
        }
    }

    private fun setupNavGraph(@NavigationRes graphResId: Int) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.graph = navGraph
    }

    private fun iniUI() {
        pbActivityLoader = findViewById(R.id.pb_activity_loader)
        toolbar = findViewById(R.id.tb_library)
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setHomeButtonEnabled(true)
        actionBar.setDisplayShowTitleEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

    private fun setupPlayerViewModel() {
        playerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]
        lifecycle.addObserver(playerViewModel.lifecycleObserver)
        startPlayer()
    }

    private fun startPlayer() {
        val serviceIntent = Intent(this, PlayerService::class.java)
        startService(serviceIntent)
        bindService(
            serviceIntent,
            playerViewModel.playerServiceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    private fun stopPlayer() {
        lifecycle.removeObserver(playerViewModel.lifecycleObserver)
        unbindService(playerViewModel.playerServiceConnection)
        val serviceIntent = Intent(this, PlayerService::class.java)
        stopService(serviceIntent)
    }

    override fun onDestroy() {
        stopPlayer()
        super.onDestroy()
    }

    override fun onLoading(loading: Boolean) {
        pbActivityLoader.visibility = View.VISIBLE
    }

    override fun onSuccess(response: Any) {
        val responseVal = response as Boolean
        globalDataExist = responseVal
        Log.e("SA", "eventOnUI: $globalDataExist")
        if (globalDataExist) {
            pbActivityLoader.visibility = View.GONE
            eventOnUI(dataShowType)
        } else {
            pbActivityLoader.visibility = View.GONE
            this.finish()
        }
    }

    override fun onError(errorMessage: String) {

    }

    override fun onStatusLoginOrLogout(statusMessage: Int) {

    }
}