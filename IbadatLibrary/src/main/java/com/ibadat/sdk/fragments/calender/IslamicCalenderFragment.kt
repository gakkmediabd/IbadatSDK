package com.ibadat.sdk.fragments.calender


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.IslamicCalendarAdapter
import com.ibadat.sdk.adapter.IslamicChhutiAdapter
import com.ibadat.sdk.data.model.calender.IslamicCalendarModel
import com.ibadat.sdk.data.model.islamicholidays.IslamicHolidayListResponse
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.databinding.FragmentIslamicCalenderBinding
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.util.CalendarUtility
import com.ibadat.sdk.util.CustomAnimation
import com.ibadat.sdk.util.LanguageConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*


private const val ARG_DETAILS_CALL_BACK = "detailsCallBack"

internal class IslamicCalenderFragment : Fragment() {

    private lateinit var binding: FragmentIslamicCalenderBinding

    //lateinit var toolbar: Toolbar
    private var currentMonthIndex = 0
    private var mDataAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: GridLayoutManager? = null

    private var mResponseData: ArrayList<IslamicCalendarModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          //  mCallback = it.getSerializable(ARG_DETAILS_CALL_BACK) as? DetailsCallBack
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            IslamicCalenderFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable(ARG_DETAILS_CALL_BACK, callback)
//                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_islamic_calender,
                container,
                false
            )



        return binding.root
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

        Log.e("THE MONTH ", "uCal[2]"+uCal[2]+" i:"+i)
        val instance = CustomIslamicCalendarData
        binding.calenderView.txtVwArobiDate.text = instance.getIslamicToDay(requireContext(), i)
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
        val textView: TextView = binding.calenderView.txtVwEnglishDate
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
    binding.calenderView.progressBar.visibility = View.VISIBLE
        var api: ApiService = RetroClient.getIslamicHolidaysApiService()!!
        val call = api.getIslamicHolidays("627c8495c44068285dde96c2","undefined","1")
        Log.d(
            "TAG",
            "onResponse: holiday loaded: "
        )
        call.enqueue(object : Callback<IslamicHolidayListResponse> {
            override fun onResponse(
                call: Call<IslamicHolidayListResponse>,
                response: Response<IslamicHolidayListResponse>
            ) {
                binding.calenderView.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    Log.d(
                        "TAG",
                        "onResponse: surah loaded: " + (response.body()?.data!![1].text)
                    )
                    val quranDetailsModel: IslamicHolidayListResponse? = response.body()
                    if (quranDetailsModel != null) {
                        loadRV(quranDetailsModel)
                    }
                }
            }

            override fun onFailure(call: Call<IslamicHolidayListResponse>, t: Throwable) {
                Log.d(
                    "TAG",
                    "onFailure: " + t.message
                )
                binding.calenderView.progressBar.visibility = View.GONE
//                Toast.makeText(this@PobitroQuranDetailsActivity, "" + t.message, Toast.LENGTH_SHORT)
//                    .show()
            }
        })

    }

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
        binding.calenderView.llNext.setOnClickListener { nextPrevious(1) }
        binding.calenderView.llLftPrevious.setOnClickListener { nextPrevious(2) }
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
            binding.calenderView.recyclerViewIslamicChhuti.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val adapter = IslamicChhutiAdapter(quranDetailsModel.data)
            binding.calenderView.recyclerViewIslamicChhuti.adapter = CustomAnimation.getAnimatedRecyclerView(adapter)
            binding.calenderView.recyclerViewIslamicChhuti.isNestedScrollingEnabled = false
        }
    }
    private fun initFunctionality() {

        val gridLayoutManager2 = GridLayoutManager(requireActivity(), 7)
        this.mLayoutManager = gridLayoutManager2
        binding.calenderView.recyclerViewIslamicCal.layoutManager = gridLayoutManager2
        val arrayList2: ArrayList<IslamicCalendarModel> = ArrayList()
        this.mResponseData = arrayList2
        val islamicCalendarAdapter = IslamicCalendarAdapter(arrayList2)
        this.mDataAdapter = islamicCalendarAdapter
        binding.calenderView.recyclerViewIslamicCal.adapter = islamicCalendarAdapter

        val mCal = Calendar.getInstance(Locale.getDefault())
        mCal.time = Date()
        mCal.add(Calendar.DAY_OF_MONTH, -1)

        val uCal = UmmalquraCalendar()
        uCal.time = mCal.time

        this.currentMonthIndex = uCal[2]
    }





}
