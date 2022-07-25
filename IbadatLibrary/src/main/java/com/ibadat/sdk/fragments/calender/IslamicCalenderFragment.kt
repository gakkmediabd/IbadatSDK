package com.ibadat.sdk.fragments.calender


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.IslamicCalendarAdapter
import com.ibadat.sdk.adapter.IslamicChhutiAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.model.calender.IslamicCalendarModel
import com.ibadat.sdk.data.model.islamicholidays.IslamicHolidayListResponse
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.util.CalendarUtility
import com.ibadat.sdk.util.CustomAnimation
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.views.MyCustomTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*


internal class IslamicCalenderFragment : BaseFragment() {
    private var currentMonthIndex = 0
    private var mDataAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: GridLayoutManager? = null
    private var mResponseData: ArrayList<IslamicCalendarModel>? = null

    private lateinit var cvW: CardView
    private lateinit var llLeftPrevious: LinearLayout
    private lateinit var ctvArabicDate: MyCustomTextView
    private lateinit var ctvEnglishDate: MyCustomTextView
    private lateinit var llRightNext: LinearLayout
    private lateinit var rvIslamicCalender: RecyclerView
    private lateinit var pbProgress: ProgressBar
    private lateinit var rvIslamicChhuti: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_islamic_calender, container, false)
        val includeCalenderView: RelativeLayout = view.findViewById(R.id.include_calender_view)
        cvW = includeCalenderView.findViewById(R.id.cv_w)
        llLeftPrevious = includeCalenderView.findViewById(R.id.ll_left_previous)
        ctvArabicDate = includeCalenderView.findViewById(R.id.ctv_Arabic_date)
        ctvEnglishDate = includeCalenderView.findViewById(R.id.ctv_english_date)
        llRightNext = includeCalenderView.findViewById(R.id.ll_right_next)
        rvIslamicCalender = includeCalenderView.findViewById(R.id.rv_islamic_calender)
        pbProgress = includeCalenderView.findViewById(R.id.pb_progress)
        rvIslamicChhuti = includeCalenderView.findViewById(R.id.rv_islamic_chhuti)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFunctionality()
        initialListener()
        loadData()
        // initActionBar()
    }

    private fun loadData() {
        loadIslamicChhutiData()
        loadIslamicCalendar(currentMonthIndex)
    }

    private fun loadIslamicCalendar(vararg iArr: Int) {
        val mCal = Calendar.getInstance(Locale.getDefault())
        mCal.time = Date()
        mCal.add(Calendar.DAY_OF_MONTH, -1)

        val uCal = UmmalquraCalendar()
        uCal.time = mCal.time

        var i = uCal[2]
        if (iArr.isNotEmpty()) {
            i = iArr[0]
        }

        Log.e("THE MONTH ", "uCal[2]" + uCal[2] + " i:" + i)
        val instance = CustomIslamicCalendarData
        ctvArabicDate.text = instance.getIslamicToDay(requireContext(), i)
        val instance2 = Calendar.getInstance()
        val calendarUtility = CalendarUtility()
        val weekNameBn: String =
            calendarUtility.getWeekName(Calendar.getInstance(), requireContext())
        val sb = StringBuilder()
        sb.append(LanguageConverter.getNumberByLocale(instance2[5].toString()))
        sb.append(" ")
        sb.append(calendarUtility.getMonthName(requireContext(), instance2[2]))
        sb.append(" ")
        sb.append(
            ("" + LanguageConverter.getNumberByLocale(
                calendarUtility.getCurrentYear().toString()
            )).substring(2)
        )
        val sb2 = sb.toString()
        val textView: TextView = ctvEnglishDate
        textView.text = ("$weekNameBn, $sb2")
        val islamicMonthData: ArrayList<IslamicCalendarModel> = instance.getIslamicMonthData(i)
        mResponseData?.clear()
        mResponseData?.addAll(islamicMonthData)
        mDataAdapter?.notifyDataSetChanged()
    }

    //    private fun initActionBar() {
//        toolbar = requireActivity().findViewById(R.id.toolbar)
//        toolbar.setNavigationIcon(R.drawable.hadith_icon_back)
//        toolbar.setNavigationOnClickListener {
//            if (SurahActivity.backPressCount == 0) {
//                requireActivity().finish()
//            }
//        }
//    }
    private fun loadIslamicChhutiData() {
        pbProgress.visibility = View.VISIBLE
        val api: ApiService = RetroClient.getIslamicHolidaysApiService()!!
        val call = api.getIslamicHolidays("627c8495c44068285dde96c2", "undefined", "1")
        Log.d(
            "TAG",
            "onResponse: holiday loaded: "
        )
        call.enqueue(object : Callback<IslamicHolidayListResponse> {
            override fun onResponse(
                call: Call<IslamicHolidayListResponse>,
                response: Response<IslamicHolidayListResponse>
            ) {
                pbProgress.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val quranDetailsModel: IslamicHolidayListResponse? = response.body()
                    if (quranDetailsModel != null) {
                        loadRV(quranDetailsModel)
                    }
                }
            }

            override fun onFailure(call: Call<IslamicHolidayListResponse>, t: Throwable) {
                pbProgress.visibility = View.GONE
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun loadJSONFromAsset(str: String): String {
        return try {
            val open: InputStream = requireActivity().assets.open(str)
            val bArr = ByteArray(open.available())
            open.read(bArr)
            open.close()
            String(bArr, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    private fun initialListener() {
        llRightNext.setOnClickListener { nextPrevious(1) }
        llLeftPrevious.setOnClickListener { nextPrevious(2) }
    }

    private fun nextPrevious(i: Int) {
        if (i == 1) {
            this.currentMonthIndex++
        } else if (i == 2) {
            this.currentMonthIndex--
        }
        loadIslamicCalendar(this.currentMonthIndex)
    }

    private fun loadRV(quranDetailsModel: IslamicHolidayListResponse) {
        if (quranDetailsModel.data != null) {
            rvIslamicChhuti.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val adapter = IslamicChhutiAdapter(quranDetailsModel.data)
            rvIslamicChhuti.adapter =
                CustomAnimation.getAnimatedRecyclerView(adapter)
            rvIslamicChhuti.isNestedScrollingEnabled = false
        }
    }

    private fun initFunctionality() {

        val gridLayoutManager2 = GridLayoutManager(requireActivity(), 7)
        this.mLayoutManager = gridLayoutManager2
        rvIslamicCalender.layoutManager = gridLayoutManager2
        val arrayList2: ArrayList<IslamicCalendarModel> = ArrayList()
        this.mResponseData = arrayList2
        val islamicCalendarAdapter = IslamicCalendarAdapter(arrayList2)
        this.mDataAdapter = islamicCalendarAdapter
        rvIslamicCalender.adapter = islamicCalendarAdapter

        val mCal = Calendar.getInstance(Locale.getDefault())
        mCal.time = Date()
        mCal.add(Calendar.DAY_OF_MONTH, -1)

        val uCal = UmmalquraCalendar()
        uCal.time = mCal.time

        this.currentMonthIndex = uCal[2]
    }


}
