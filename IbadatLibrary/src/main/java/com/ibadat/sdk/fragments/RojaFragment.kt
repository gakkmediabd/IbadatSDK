package com.ibadat.sdk.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azan.Time
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.RojaRVAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.broadcust_recever.AlarmRequestBReceiver
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.app_model.DivisionModel
import com.ibadat.sdk.data.model.roza.IfterAndSehriTime
import com.ibadat.sdk.roza.AllAzanTimeProvider
import com.ibadat.sdk.roza.TimeFormtter
import com.ibadat.sdk.util.*
import com.ibadat.sdk.views.MyCustomTextView


internal class RojaFragment : BaseFragment() {

    private lateinit var ctvCurrentDate: MyCustomTextView
    private lateinit var spDivision: Spinner

    private lateinit var ctvSahriMessage: MyCustomTextView
    private lateinit var ctvSahriTime: MyCustomTextView
    private lateinit var ivSahriAlarm: ImageView

    private lateinit var ctvIftarMessage: MyCustomTextView
    private lateinit var ctvIftarTime: MyCustomTextView
    private lateinit var ivIftarAlarmOff: ImageView

    private lateinit var ctvIfterShareTimeTable: MyCustomTextView
    private lateinit var llRojaTimesLayout: LinearLayout
    private lateinit var rvRojaDateTimes: RecyclerView

    private lateinit var ctvRojarDua: MyCustomTextView
    private lateinit var llRojarDua: LinearLayout

    private lateinit var ctvRojaRule: MyCustomTextView
    private lateinit var llRojaRule: LinearLayout

    private lateinit var imageView: ImageView
    private lateinit var imageViewRoja: ImageView
    private lateinit var mListDivisionModel: MutableList<DivisionModel>
    private var mSelectedLat = 0.0
    private var mSelectedLong = 0.0
    private var mSahriTime = ""
    private var mIfterTime = ""
    private var isShowOrHide = false
    private lateinit var mAlarmManager: AlarmManager
    private lateinit var mPendingIntent: PendingIntent
    private lateinit var mRojaTime: IfterAndSehriTime
    private lateinit var mRojaAdapter: RojaRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_roja, container, false)
        viewInitialize(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUi()
    }

    private fun viewInitialize(view: View) {
        ctvCurrentDate = view.findViewById(R.id.ctv_current_date)
        spDivision = view.findViewById(R.id.sp_division)

        ctvSahriMessage = view.findViewById(R.id.ctv_sahri_message)
        ctvSahriTime = view.findViewById(R.id.ctv_sahri_time)
        ivSahriAlarm = view.findViewById(R.id.iv_sahri_alarm)

        ctvIftarMessage = view.findViewById(R.id.ctv_iftar_message)
        ctvIftarTime = view.findViewById(R.id.ctv_iftar_time)
        ivIftarAlarmOff = view.findViewById(R.id.iv_iftar_alarm_off)

        ctvIfterShareTimeTable = view.findViewById(R.id.ctv_ifter_share_time_table)
        llRojaTimesLayout = view.findViewById(R.id.ll_roja_times_layout)
        rvRojaDateTimes = view.findViewById(R.id.rv_roja_date_times)

        ctvRojarDua = view.findViewById(R.id.ctv_rojar_dua)
        llRojarDua = view.findViewById(R.id.ll_rojar_dua)

        ctvRojaRule = view.findViewById(R.id.ctv_roja_rule)
        llRojaRule = view.findViewById(R.id.ll_roja_rule)
        imageView = view.findViewById(R.id.imageRojaTitle)
        imageViewRoja = view.findViewById(R.id.imageRojaTitle2)
        imageView.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                "drawable-hdpi/ramadan_ttl_shape.png"
            )
        )
        imageViewRoja.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                "drawable-hdpi/ramadan_ttl_shape.png"
            )
        )
    }

    private fun initializeUi() {
        mRojaAdapter = RojaRVAdapter()
        loadDivision()

        checkAlarmStatus(ivSahriAlarm, AppPreference.isSahriAlarmOnOff)
        checkAlarmStatus(ivIftarAlarmOff, AppPreference.isIfterAlarmOnOff)

        ivSahriAlarm.setOnClickListener {
            val sahriTimeMilli =
                TimeFormtter.get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
                    mRojaTime.dateMs,
                    mRojaTime.sehriTIme
                )
            if (AppConstantUtils.checkCurrentAndNextAzanTime(
                    requireContext(),
                    sahriTimeMilli
                )
            )
                if (!AppPreference.isSahriAlarmOnOff) {
                    AppPreference.isSahriAlarmOnOff = true
                    checkAlarmStatus(ivSahriAlarm, AppPreference.isSahriAlarmOnOff)

                    setAlarm(sahriTimeMilli, START_SEHRI_ALARM_SERVICE, sahriTimeMilli)
                } else {
                    AppPreference.isSahriAlarmOnOff = false
                    checkAlarmStatus(ivSahriAlarm, AppPreference.isSahriAlarmOnOff)
                    cancelAlarm(DISMISS_CURRENT_NOTIFICATION_SERVICE, sahriTimeMilli)
                }
        }

        ivIftarAlarmOff.setOnClickListener {
            val ifterTimeMilli =
                TimeFormtter.get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
                    mRojaTime.dateMs,
                    mRojaTime.ifterTime
                )
            if (AppConstantUtils.checkCurrentAndNextAzanTime(
                    requireContext(),
                    ifterTimeMilli
                )
            )
                if (!AppPreference.isIfterAlarmOnOff) {
                    AppPreference.isIfterAlarmOnOff = true
                    checkAlarmStatus(ivIftarAlarmOff, AppPreference.isIfterAlarmOnOff)

                    setAlarm(ifterTimeMilli, START_IFTER_ALARM_SERVICE, ifterTimeMilli)
                } else {
                    AppPreference.isIfterAlarmOnOff = false
                    checkAlarmStatus(ivIftarAlarmOff, AppPreference.isIfterAlarmOnOff)
                    cancelAlarm(DISMISS_CURRENT_NOTIFICATION_SERVICE, ifterTimeMilli)
                }
        }

        rojaDataVisibilityControl(
            ctvIfterShareTimeTable,
            llRojaTimesLayout,
            llRojarDua,
            llRojaRule
        )
        rojaDataVisibilityControl(
            ctvRojarDua,
            llRojarDua,
            llRojaTimesLayout,
            llRojaRule
        )
        rojaDataVisibilityControl(
            ctvRojaRule,
            llRojaRule,
            llRojarDua,
            llRojaTimesLayout
        )
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

    private fun rojaDataVisibilityControl(
        eventControl: MyCustomTextView,
        activeViewControl: LinearLayout,
        hideViewControl1: LinearLayout,
        hideViewControl2: LinearLayout
    ) {
        eventControl.setOnClickListener {
            if (!isShowOrHide) {
                isShowOrHide = true
                activeViewControl.visibility = View.VISIBLE
                hideViewControl1.visibility = View.GONE
                hideViewControl2.visibility = View.GONE
            } else {
                isShowOrHide = false
                activeViewControl.visibility = View.GONE
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag", "UseCompatLoadingForDrawables")
    private fun checkAlarmStatus(ivAlarmControl: ImageView, alarmStatus: Boolean) {
        if (alarmStatus) {
            ivAlarmControl.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_notifications_off
                )
            )
        } else {
            ivAlarmControl.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.ic_notification_white
                )
            )
        }
    }

    private fun loadDivision() {
        mListDivisionModel = AppPrefUtils.getDivision()
        val strArrayDivision = ArrayList<String>()
        for (division in mListDivisionModel) {
            strArrayDivision.add(division.divisionBanglaName)
        }
        spDivision.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            strArrayDivision
        )
        spDivision.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                val mDivision = mListDivisionModel[position]
                mDivision.apply {
                    mSelectedLat = geoLat.toDouble()
                    mSelectedLong = geoLong.toDouble()
                }
                loadCurrentLocationWizRojaTime(mSelectedLat, mSelectedLong)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun loadCurrentLocationWizRojaTime(
        geoLat: Double,
        geoLong: Double
    ) {
        ctvCurrentDate.text = AppPrefUtils.getCurrentDateAndWeek(requireContext())
        mRojaTime = AllAzanTimeProvider
            .getRojaTimeByDateAndOffsetDay(
                geoLat,
                geoLong,
                TimeFormtter.getCurrentDate(),
                TimeFormtter.getCurrentDay()
            )
        mRojaTime.apply {
            ctvSahriTime.text = sehriTimeStr
            ctvIftarTime.text = ifterTimeStr

            mSahriTime = TimeFormtter.getMillSecondFormattedStringFromTime(
                sehriTIme
            )
            mIfterTime =
                TimeFormtter.getMillSecondFormattedStringFromTime(
                    Time(ifterTime.hour - 12, ifterTime.minute, 0, false)
                )
        }

        mRojaAdapter.setRojaTimes(
            AllAzanTimeProvider.get10DaysSehriAndIftarTime(
                geoLat,
                geoLong
            )
        )

        rvRojaDateTimes.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mRojaAdapter
        }
    }
}