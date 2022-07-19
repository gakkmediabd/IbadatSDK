package com.ibadat.sdk.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.reflect.TypeToken
import com.ibadat.sdk.R
import com.ibadat.sdk.activities.HomeIbadatSdkActivity
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.broadcust_recever.AlarmRequestBReceiver
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.app_model.CurrentDayAzansModel
import com.ibadat.sdk.data.model.app_model.DueAzanModel
import com.ibadat.sdk.roza.AllAzanTimeProvider
import com.ibadat.sdk.roza.TimeFormtter
import com.ibadat.sdk.util.*
import com.ibadat.sdk.views.MyCustomTextView
import java.util.*
import kotlin.math.floor

internal class PrayerTimeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var prayerHandler: Handler
    private lateinit var prayerRunnable: Runnable

    private lateinit var pbLoader: ProgressBar
    private lateinit var srlAzanTime: SwipeRefreshLayout
    private lateinit var btnLocation: Button
    private lateinit var ctvCurrentTime: MyCustomTextView
    private lateinit var pbCircle: ProgressBar
    private lateinit var ctvNextNamazTitle: MyCustomTextView
    private lateinit var ctvNextNamazTime: MyCustomTextView
    private lateinit var ctvCurrentNamazStatus: MyCustomTextView
    private lateinit var ctvNextNamaz: MyCustomTextView
    private lateinit var ctvPreviousNamaz: MyCustomTextView
    private lateinit var ctvSelectedDate: MyCustomTextView
    private lateinit var ctvSunriseMessage: MyCustomTextView
    private lateinit var ivSunriseNotification: ImageView
    private lateinit var llAzanTimeLayout: LinearLayout
    private lateinit var imageview: ImageView

    //want to only one time call Azan library. so can decrease execution
    private lateinit var globalGenerateAzansFromLibrary: CurrentDayAzansModel
    private lateinit var globalCurrentDateAzan: DueAzanModel
    private val TAG = "PTF"
    private var globalLatitude = "0.0"
    private var globalLongitude = "0.0"
    private lateinit var globalDate: Date
    var listPrefDueAzanModel: MutableList<DueAzanModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_prayer_time, container, false)
        viewInitialize(view)
        globalDate = TimeFormtter.getCurrentDate()
        generateAzanLibrary()
        generateCurrentDayAzan()

        Log.e(TAG, "onCreateView: ")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pbLoader.visibility = View.VISIBLE
        initializeUi()
        getSharePrefData()
        callSwipeRefresh()
        generateAzanLibrary()
        generateCurrentDayAzan()

        //initPrayerTimeHandler()

        ctvPreviousNamaz.setOnClickListener {
            globalDate = TimeFormtter.decrementDateByOne(globalDate)
            loadPreviousOrNextCurrentAzanTime(globalDate)
        }

        ctvNextNamaz.setOnClickListener {
            globalDate = TimeFormtter.incrementDateByOne(globalDate)
            loadPreviousOrNextCurrentAzanTime(globalDate)
        }

        ivSunriseNotification.setOnClickListener {
            if (AppConstantUtils.checkCurrentAndNextAzanTime(
                    requireContext(), globalCurrentDateAzan.sunrise12HrTimeMilliSecond
                )
            ) {
                if (!AppPreference.isSunriseAlarmOnOff) {
                    setAlarm(
                        globalCurrentDateAzan.sunrise12HrTimeMilliSecond,
                        START_SUNRISE_ALARM_SERVICE,
                        globalCurrentDateAzan.sunrise12HrTimeMilliSecond
                    )
                } else {
                    cancelAlarm(
                        DISMISS_CURRENT_NOTIFICATION_SERVICE,
                        globalCurrentDateAzan.sunrise12HrTimeMilliSecond
                    )
                }
                AppPreference.isSunriseAlarmOnOff =
                    setAzanAlarmImage(ivSunriseNotification, !AppPreference.isSunriseAlarmOnOff)
            }
        }
        Log.e(TAG, "onViewCreated: ")
    }

    private fun viewInitialize(view: View) {
        pbLoader = view.findViewById(R.id.pb_loader)
        srlAzanTime = view.findViewById(R.id.srl_azan_time)
        btnLocation = view.findViewById(R.id.btn_location)
        ctvCurrentTime = view.findViewById(R.id.ctv_current_time)
        pbCircle = view.findViewById(R.id.pb_circle)
        ctvNextNamazTitle = view.findViewById(R.id.ctv_next_namaz_title)
        ctvNextNamazTime = view.findViewById(R.id.ctv_next_namaz_time)
        ctvPreviousNamaz = view.findViewById(R.id.ctv_previous_namaz)
        ctvCurrentNamazStatus = view.findViewById(R.id.ctv_current_namaz_status)
        ctvNextNamaz = view.findViewById(R.id.ctv_next_namaz)
        ctvSelectedDate = view.findViewById(R.id.ctv_selected_date)
        ctvSunriseMessage = view.findViewById(R.id.ctv_sunrise_message)
        ivSunriseNotification = view.findViewById(R.id.iv_sunrise_notification)
        llAzanTimeLayout = view.findViewById(R.id.ll_azan_time_layout)
    }

    private fun initializeUi() {
        val countryName = AppPreference.lastLocationName
        if (countryName != "") {
            btnLocation.text = countryName
            setCurrentTime()
            setNextAzanStatus()
        } else {
            btnLocation.text = countryName
            globalLatitude = AppPreference.lastLatitude
            globalLongitude = AppPreference.lastLongitude
        }
        loadPreviousOrNextCurrentAzanTime(globalDate)
        pbLoader.visibility = View.GONE

        imageview = requireView().findViewById(R.id.imgBackgroundimage)
        imageview.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "namaz_shikka_bg.png"
            )
        )
    }

    private fun generateAzanLibrary() {
        globalGenerateAzansFromLibrary =
            AllAzanTimeProvider.getCurrentDateAzanTimeByDate(TimeFormtter.getCurrentDate())
    }

    private fun generateCurrentDayAzan() {
        globalCurrentDateAzan =
            globalGenerateAzansFromLibrary.getCurrentDateAzan(requireContext())
    }

    private fun setCurrentTime() {
        ctvCurrentTime.text =
            LanguageConverter.getDateInBangla(TimeFormtter.getCurrentTimehhmmaa())
    }

    private fun setNextAzanTime(strDate: String) {
        ctvNextNamazTime.text = strDate
    }

    private fun setNextAzanStatus() {
        globalCurrentDateAzan.apply {
            ctvNextNamazTitle.text = nextAzanName
            ctvCurrentNamazStatus.text = statusMessage
        }
    }

    private fun callSwipeRefresh() {
        srlAzanTime.isEnabled = true
        srlAzanTime.setColorSchemeResources(R.color.colorAccent)
        srlAzanTime.setOnRefreshListener {
            globalDate = TimeFormtter.getCurrentDate()
            initializeUi()
            srlAzanTime.isRefreshing = false
        }
    }

    private fun getSharePrefData() {
        try {
            listPrefDueAzanModel =
                AppPreference.getAnyObjectFromSharedPreferences(
                    CURRENT_DATE_ALARM_TIMES,
                    object : TypeToken<List<DueAzanModel>>() {}.type
                ) as MutableList<DueAzanModel>
        } catch (ignored: Exception) {
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPreviousOrNextCurrentAzanTime(
        date: Date
    ) {
        getSharePrefData()
        //re call library. because of Date will change. when date will increase or decreases
        val mNextOrPreviousAzanTimes = AllAzanTimeProvider
            .getCurrentDateAzanTimeByDate(date)
            .getSelectedAzans(requireContext())
        TimeFormtter.getCalenderDate(date)
            .apply {
                ctvSelectedDate.text = (AppPrefUtils.getBanglaWeekName(weekId) + ", "
                        + LanguageConverter.convertToNumber(day.toString()) + " "
                        + AppPrefUtils.getBanglaMonthNameByMonthNumber(month) + " "
                        + LanguageConverter.convertToNumber(year.toString()))
            }

        //at frist clear all view then re set view. becuase of refresh the UI
        llAzanTimeLayout.removeAllViews()
//        var azanTimeView: AzanDateTimeItem
        var azanTimeView: View
        for (previousOrNextAzan in mNextOrPreviousAzanTimes) {
            ctvSunriseMessage.text =
                getString(R.string.sokal) + " " + previousOrNextAzan.sunrise12HrTime

            azanTimeView = addCurrentDateAzanLayout(llAzanTimeLayout, previousOrNextAzan)
            llAzanTimeLayout.addView(azanTimeView)
            azanTimeView.setOnClickListener {
                //recall the Azan data becuase whene insert/update/delete will re read data from pref
                getSharePrefData()
                onClickAzanOnOrOff(
                    previousOrNextAzan
                )
                val imageView = it.findViewById<ImageView>(R.id.iv_notification)
                setAzanAlarmImage(
                    imageView,
                    checkDataAreExist(previousOrNextAzan)
                )
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun addCurrentDateAzanLayout(
        root: ViewGroup,
        dueAzan: DueAzanModel
    ): View {
        val azanItemView = LayoutInflater.from(requireContext())
            .inflate(R.layout.azan_date_time_item, root, false)
            .apply {
                findViewById<MyCustomTextView>(R.id.ctv_azan_name)
                    .apply {
                        text = dueAzan.nextAzanName
                    }
                findViewById<MyCustomTextView>(R.id.ctv_day_type_name)
                    .apply {
                        text = dueAzan.dayTypeName
                    }
                findViewById<MyCustomTextView>(R.id.ctv_azan_time)
                    .apply {
                        text = dueAzan.nextAzan12HrTime
                    }
                findViewById<MyCustomTextView>(R.id.ctv_meridiem_type)
                    .apply {
                        text = dueAzan.meridiemType
                    }
                findViewById<ImageView>(R.id.iv_notification).apply {
                    setAzanAlarmImage(this, checkDataAreExist(dueAzan))
                }
            }
        return azanItemView
    }

    private fun onClickAzanOnOrOff(
        salahTime: DueAzanModel
    ) {
        // When your system's current date >= your chosen date is big then you can set the alarm
        when (salahTime.nextAzanName) {
            getString(R.string.fojor_ar_okto) -> {
                if (AppConstantUtils.checkCurrentAndNextAzanTime(
                        requireContext(), salahTime.nextAzan12HrTimeMilliSecond
                    )
                )
                    if (!checkDataAreExist(salahTime)) {
                        listPrefDueAzanModel.add(
                            salahTime
                        )
                        setAlarm(
                            salahTime.nextAzan12HrTimeMilliSecond,
                            START_FAJR_ALARM_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    } else {
                        checkAndRemoveTheStoredAzanTime(
                            listPrefDueAzanModel,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                        cancelAlarm(
                            DISMISS_CURRENT_NOTIFICATION_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    }
            }
            getString(R.string.dhor_ar_okto) -> {
                if (AppConstantUtils.checkCurrentAndNextAzanTime(
                        requireContext(), salahTime.nextAzan12HrTimeMilliSecond
                    )
                )
                    if (!checkDataAreExist(salahTime)) {
                        listPrefDueAzanModel.add(
                            salahTime
                        )
                        setAlarm(
                            salahTime.nextAzan12HrTimeMilliSecond,
                            START_DHUHR_ALARM_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    } else {
                        checkAndRemoveTheStoredAzanTime(
                            listPrefDueAzanModel,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                        cancelAlarm(
                            DISMISS_CURRENT_NOTIFICATION_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    }
            }
            getString(R.string.asor_ar_okto) -> {
                if (AppConstantUtils.checkCurrentAndNextAzanTime(
                        requireContext(), salahTime.nextAzan12HrTimeMilliSecond
                    )
                )
                    if (!checkDataAreExist(salahTime)) {
                        listPrefDueAzanModel.add(
                            salahTime
                        )
                        setAlarm(
                            salahTime.nextAzan12HrTimeMilliSecond,
                            START_ASR_ALARM_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    } else {
                        checkAndRemoveTheStoredAzanTime(
                            listPrefDueAzanModel,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                        cancelAlarm(
                            DISMISS_CURRENT_NOTIFICATION_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    }
            }
            getString(R.string.magrib_ar_okto) -> {
                if (AppConstantUtils.checkCurrentAndNextAzanTime(
                        requireContext(), salahTime.nextAzan12HrTimeMilliSecond
                    )
                )
                    if (!checkDataAreExist(salahTime)) {
                        listPrefDueAzanModel.add(
                            salahTime
                        )
                        setAlarm(
                            salahTime.nextAzan12HrTimeMilliSecond,
                            START_MAGRIB_ALARM_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    } else {
                        checkAndRemoveTheStoredAzanTime(
                            listPrefDueAzanModel,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                        cancelAlarm(
                            DISMISS_CURRENT_NOTIFICATION_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    }
            }
            getString(R.string.isha_ar_okto) -> {
                if (AppConstantUtils.checkCurrentAndNextAzanTime(
                        requireContext(),
                        salahTime.nextAzan12HrTimeMilliSecond
                    )
                )
                    if (!checkDataAreExist(salahTime)) {
                        listPrefDueAzanModel.add(
                            salahTime
                        )
                        setAlarm(
                            salahTime.nextAzan12HrTimeMilliSecond,
                            START_ISHA_ALARM_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    } else {
                        checkAndRemoveTheStoredAzanTime(
                            listPrefDueAzanModel,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                        cancelAlarm(
                            DISMISS_CURRENT_NOTIFICATION_SERVICE,
                            salahTime.nextAzan12HrTimeMilliSecond
                        )
                    }
            }
        }
        AppPreference.storeAnyDataPreferences(
            CURRENT_DATE_ALARM_TIMES,
            listPrefDueAzanModel
        )
    }

    private fun checkDataAreExist(salahAzanTime: DueAzanModel): Boolean {
        val noAlarmSetOnThisTime =
            (listPrefDueAzanModel.indexOfFirst { it.nextAzan12HrTimeMilliSecond == salahAzanTime.nextAzan12HrTimeMilliSecond } != -1)
        return if (noAlarmSetOnThisTime) {
            noAlarmSetOnThisTime
        } else {
            noAlarmSetOnThisTime
        }
    }

    private fun checkAndRemoveTheStoredAzanTime(
        listDueAzanModel: MutableList<DueAzanModel>,
        milliSec: Long
    ): Boolean {
        for (dueAzanModel in listDueAzanModel) {
            if (dueAzanModel.nextAzan12HrTimeMilliSecond == milliSec) {
                listPrefDueAzanModel.remove(dueAzanModel)
                return true
            }
        }
        return false
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NewApi")
    private fun setAzanAlarmImage(ivNotification: ImageView, boolStatus: Boolean): Boolean {
        return if (boolStatus) {
            ivNotification.setImageDrawable(requireContext().getDrawable(R.drawable.ic_baseline_notifications_off_24))
            true
        } else {
            ivNotification.setImageDrawable(requireContext().getDrawable(R.drawable.ic_baseline_notifications_active_24))
            false
        }
    }

    private fun initPrayerTimeHandler() {
        prayerHandler = Handler()
        prayerRunnable = object : Runnable {
            override fun run() {
                loadPbCircleCountDown()
                prayerHandler.postDelayed(this, (1000).toLong())
            }
        }
        prayerHandler.post(prayerRunnable)
    }

    private fun loadPbCircleCountDown() {
        setCurrentTime()
        generateCurrentDayAzan()
        try {
            globalCurrentDateAzan.apply {
                ctvNextNamazTitle.text = nextAzanName
                ctvCurrentNamazStatus.text = statusMessage
            }
            val currentStartedAzanTime = when (globalCurrentDateAzan.nextAzanName) {
                getString(R.string.fojor_ar_okto) ->
                    globalCurrentDateAzan.currentStartedAzan12HrTimeMilliSecond
                else ->
                    globalCurrentDateAzan.currentStartedAzan12HrTimeMilliSecond
            }
            val nextUpcomingAzanTime =
                when (globalGenerateAzansFromLibrary.allWaqtOverForTOday) {
                    true -> globalCurrentDateAzan.nextAzan12HrTimeMilliSecond
                    false -> globalCurrentDateAzan.nextAzan12HrTimeMilliSecond
                }
            val maxTimeDifference =
                nextUpcomingAzanTime - currentStartedAzanTime

            val currentTimeDifference =
                nextUpcomingAzanTime - TimeFormtter.getCurrentTimeMillihhmmssaa()

            val percentage: Double =
                floor(100.00f - ((currentTimeDifference.toDouble() / maxTimeDifference) * 100))

            setNextAzanTime(LanguageConverter.getDateInBangla(globalGenerateAzansFromLibrary.timeLeft))
            setNextAzanStatus()
            pbCircle.progress = percentage.toInt()
        } catch (e: Exception) {
        }
    }

    private fun removePrayerTimeHandler() {
        try {
            prayerHandler.removeCallbacksAndMessages(null)
        } catch (e: Exception) {
        }
    }

    private fun setAlarm(timeMilliSec: Long, requestType: String, mRequestCode: Long) {
        val alarmRBR = AlarmRequestBReceiver()
        alarmRBR.cancelAlarm(requireContext(), requestType, mRequestCode)
        alarmRBR.setAlarm(requireContext(), timeMilliSec, requestType, mRequestCode)
    }

    private fun cancelAlarm(requestType: String, mRequestCode: Long) {
        val alarmRBR = AlarmRequestBReceiver()
        alarmRBR.cancelAlarm(requireContext(), requestType, mRequestCode)
    }

    override fun onPause() {
        super.onPause()
        removePrayerTimeHandler()
    }

    override fun onStart() {
        super.onStart()
        Log.e("PTF", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: ")
        initPrayerTimeHandler()
        initToolbar(requireActivity())
            .setNavigationOnClickListener {
                if (HomeIbadatSdkActivity.backPressCount == 0) {
                    removePrayerTimeHandler()
                    requireActivity().finish()
                }
            }
    }

    override fun onStop() {
        super.onStop()
        //removePrayerTimeHandler()
    }

    override fun onClick(v: View?) {
    }
}