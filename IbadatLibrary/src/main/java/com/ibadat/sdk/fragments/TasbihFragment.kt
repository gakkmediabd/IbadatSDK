package com.ibadat.sdk.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ibadat.sdk.R
import com.ibadat.sdk.activities.HomeIbadatSdkActivity
import com.ibadat.sdk.adapter.TasbihHistoryAdapter
import com.ibadat.sdk.adapter.TasbihRVAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.call_back.CountControl
import com.ibadat.sdk.call_back.PressListener
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.app_model.TasbihModel
import com.ibadat.sdk.roza.AzanPlayer
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.util.handleClickEvent
import com.ibadat.sdk.views.TextViewNormal


internal class TasbihFragment : BaseFragment(), CountControl, PressListener {
    lateinit var toolbar: Toolbar

    private lateinit var pbCircle: ProgressBar
    private lateinit var acivCirleTasbih: AppCompatImageView
    private lateinit var acivPattern: AppCompatImageView
    private lateinit var tvCount: TextViewNormal
    private lateinit var tvTimes: TextViewNormal
    private lateinit var clRound: ConstraintLayout
    private lateinit var tvRoundCount: TextViewNormal
    private lateinit var acivMidSeparator: AppCompatImageView
    private lateinit var tvTotalCount: TextView
    private lateinit var rvTasbihItem: RecyclerView
    private lateinit var tvYourCount: TextViewNormal
    private lateinit var rvHistory: RecyclerView
    private lateinit var rlControlBar: RelativeLayout
    private lateinit var acivResetAll: AppCompatImageView
    private lateinit var acivStartCount: AppCompatImageView
    private lateinit var acivOnOffSound: AppCompatImageView

    private var banglaDuaArrSize = 0
    private var arabicDuaArrSize = 0
    private var mLocalCount = 0
    private var mTotalCount = 0
    private var mRound = 0
    private var mUserSelectCount = 33
    private var mCheckSoundOnOff: Boolean = true
    private var mFlag1 = 0

    //    private lateinit var mpPlaySound: MediaPlayer
    private lateinit var strBanglaDuaArray: Array<String>
    private lateinit var strArabicDuaArray: Array<String>
    private var selectedItem = "0"
    private lateinit var radioGroup: RadioGroup
    private lateinit var rbThreeThree: RadioButton
    private lateinit var rbThreeFour: RadioButton
    private lateinit var rbNineNine: RadioButton

    lateinit var mlistTasbihModel: MutableList<TasbihModel>
    private var duaIndex: Array<String> = arrayOf("0", "1", "2", "3", "4", "5", "6", "7")
    private var viewClickedIndex: Int = 0
    private var buttonClickedIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasbih, container, false)
        viewInitialize(view)
        Log.e("TF", "onCreateView: ")
        initToolbar(requireActivity())
            .setNavigationOnClickListener {
                if (HomeIbadatSdkActivity.backPressCount == 0) {
                    requireActivity().finish()
                }
            }
        return view
    }

    private fun viewInitialize(view: View) {
        pbCircle = view.findViewById(R.id.pb_circle)
        acivCirleTasbih = view.findViewById(R.id.aciv_cirle_tasbih)
        acivPattern = view.findViewById(R.id.aciv_pattern)
        tvCount = view.findViewById(R.id.tv_count)
        tvTimes = view.findViewById(R.id.tv_times)
        clRound = view.findViewById(R.id.cl_round)
        radioGroup = view.findViewById(R.id.groupradio)
        rbThreeThree = view.findViewById(R.id.rb_three_three)
        rbThreeFour = view.findViewById(R.id.rb_three_four)
        rbNineNine = view.findViewById(R.id.rb_nine_nine)
        tvTotalCount = view.findViewById(R.id.tv_total_count)
        rvTasbihItem = view.findViewById(R.id.rv_tasbih_item)
        tvYourCount = view.findViewById(R.id.tv_your_count)
        rvHistory = view.findViewById(R.id.rv_history)
        rlControlBar = view.findViewById(R.id.rl_control_bar)
        acivResetAll = view.findViewById(R.id.aciv_reset_all)
        acivStartCount = view.findViewById(R.id.aciv_start_count)
        acivOnOffSound = view.findViewById(R.id.aciv_on_off_sound)
        acivStartCount.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "ic_btn_press.png"
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TF", "onViewCreated: ")
        initializeUi()

        acivStartCount.handleClickEvent {
            mLocalCount++
            calculateTotalAndRoundValue()
            setTotalValue()
            AppPreference.totalCount = mTotalCount
            handleSound()
        }

        acivOnOffSound.handleClickEvent {
            soundOnAndOffControl()
        }

        acivResetAll.handleClickEvent {
            showResetDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        resetCurrentCount()
    }

    @SuppressLint("SetTextI18n")
    private fun initializeUi() {
        mlistTasbihModel = ArrayList()

        /*acivCirleTasbih.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "tasbih_circle_bg.png"
            )
        )*/

        acivStartCount.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "ic_btn_press.png"
            )
        )

        strBanglaDuaArray =
            requireContext().resources!!.getStringArray(R.array.array_bangla_tasbih_duas)
        strArabicDuaArray =
            requireContext().resources!!.getStringArray(R.array.array_arabic_tasbih_duas)

        banglaDuaArrSize = strBanglaDuaArray.size
        arabicDuaArrSize = strArabicDuaArray.size

        for (i in 0..7) {
            mlistTasbihModel.add(
                TasbihModel(
                    i,
                    strBanglaDuaArray[i],
                    "",
                    strArabicDuaArray[i],
                    0
                )
            )
        }
        rvTasbihItem.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter =
                TasbihRVAdapter(
                    mlistTasbihModel,
                    this@TasbihFragment,
                    viewClickedIndex,
                    buttonClickedIndex
                )
        }
        selectedItem = viewClickedIndex.toString()
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_three_three -> {
                    getUserCount(33)
                }
                R.id.rb_three_four -> {
                    getUserCount(34)
                }
                R.id.rb_nine_nine -> {
                    getUserCount(99)
                }
            }
        }

        mlistTasbihModel = getModels(duaIndex, mlistTasbihModel)
        rvHistory.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = TasbihHistoryAdapter(mlistTasbihModel)
        }
//        AppPrefUtils.getMp3UriFromPath("raw/azan_fajr.mp3")
        Log.e("TF", "initializeUi: " + requireContext().filesDir.absolutePath)
        /* mpPlaySound =
             MediaPlayer.create(
                 context,
                 Util.getUriFromPath(requireContext(), "raw/tasbih_second.mp3")
             )*/
//        mpPlaySound =
//            MediaPlayer().apply {
//                setDataSource(
//                    requireContext(),
//                    Util.getUriFromPath(requireContext(), "raw/tasbih_second.mp3")
//                )
//            }
        mCheckSoundOnOff = AppPreference.soundflag
        mTotalCount = AppPreference.totalCount
        pbCircle.progress = 0
        tvCount.text = LanguageConverter.getNumberByLocale(
            LanguageConverter.getNumber(
                mLocalCount
            )
        )
        tvTimes.text =
            LanguageConverter.getNumberByLocale(mUserSelectCount.toString()) + " " + getString(
                R.string.txt_times
            )
        // tvRoundCount.text = getString(R.string.text_zero)

        if (mCheckSoundOnOff) {
            acivOnOffSound.setImageResource(R.drawable.ic_btn_sound)
        } else {
            acivOnOffSound.setImageResource(R.drawable.ic_btn_sound_off)
        }
        setTotalValue()
    }

    private fun calculateTotalAndRoundValue() {
        if (mLocalCount > mUserSelectCount) {
            if (mLocalCount < mUserSelectCount + 2) {
                mLocalCount = 0
                mRound++
                Toast.makeText(context, getString(R.string.count_complete), Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            onPressed()
            tvCount.text = LanguageConverter.getNumberByLocale(
                LanguageConverter.getNumber(
                    mLocalCount
                )
            )
            pbCircle.apply {
                max = mUserSelectCount
                progress = mLocalCount
            }
            if (mTotalCount >= 0) {
                mTotalCount++
            } else {
                mTotalCount = 1
            }
        }
    }

    private fun setTotalValue() {
        if (mTotalCount >= 0) {
            tvTotalCount.text =
                LanguageConverter.convertToNumber(LanguageConverter.getNumber(mTotalCount))
        } else {
            tvTotalCount.text = getString(R.string.text_zero)
        }
    }

    private fun handleSound() {
        if (mCheckSoundOnOff) {
            AzanPlayer.playAzanFromRawFolder(
                requireContext(),
                Util.getUriFromPath(
                    requireContext(),
                    AppConstantUtils.raw + "tasbih_second.mp3"
                )
            )
        }
    }

    private fun soundOnAndOffControl() {
        if (mCheckSoundOnOff) {
            acivOnOffSound.setImageResource(R.drawable.ic_btn_sound_off)
            mCheckSoundOnOff = false
            AppPreference.soundflag = false
        } else {
            acivOnOffSound.setImageResource(R.drawable.ic_btn_sound)
            mCheckSoundOnOff = true
            AppPreference.soundflag = true
        }
    }

    @SuppressLint("InflateParams")
    fun showResetDialog() {
        val customDialog =
            MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_rounded)
        val dialogView: View = LayoutInflater.from(requireActivity())
            .inflate(R.layout.dialog_reset_tasbih_count, null, false)
        val acivClose = dialogView.findViewById<AppCompatImageView>(R.id.aciv_close)
//        val tvTitleReset = dialogView.findViewById<TextViewNormal>(R.id.tv_title_reset)
//        val tvDesReset = dialogView.findViewById<TextViewNormal>(R.id.tv_des_reset)
        val acbTotalCount = dialogView.findViewById<AppCompatButton>(R.id.acb_total_count)
        val acbCurrentCount = dialogView.findViewById<AppCompatButton>(R.id.acb_current_count)
        customDialog.setView(dialogView)
        val alertDialog = customDialog.show()
        alertDialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        alertDialog.window?.setGravity(Gravity.CENTER)
        alertDialog.setCancelable(false)
        alertDialog.show()

        acivClose.handleClickEvent {
            alertDialog.dismiss()
        }
        acbTotalCount.handleClickEvent {
            resetTotalCount()
            alertDialog.dismiss()
        }
        acbCurrentCount.handleClickEvent {
            resetCurrentCount()
            alertDialog.dismiss()
        }
    }

    private fun resetTotalCount() {
        AppPreference.clearTotalCount()
        clearHistory(duaIndex)
        tvTotalCount.text = getString(R.string.text_zero)
        tvCount.text = getString(R.string.text_zero)
        pbCircle.progress = 0
        mLocalCount = 0
        mTotalCount = 0
    }

    private fun resetCurrentCount() {
        tvCount.text = getString(R.string.text_zero)
        pbCircle.progress = 0
        mLocalCount = 0
    }

    private fun clearHistory(ars: Array<String>) {
        for (ar in ars) {
            AppPreference.clearHistoryCount(ar)
        }
        mlistTasbihModel = getModels(duaIndex, mlistTasbihModel)
        rvHistory.adapter = TasbihHistoryAdapter(mlistTasbihModel)
    }

    override fun onPressed() {
        AppPreference.saveTashbihCount(
            AppPreference.loadTashbihCount(selectedItem) + 1,
            selectedItem
        )
        mlistTasbihModel = getModels(duaIndex, mlistTasbihModel)
        rvHistory.adapter = TasbihHistoryAdapter(mlistTasbihModel)
    }

    private fun getModels(
        index: Array<String>,
        tasbihs: MutableList<TasbihModel>
    ): MutableList<TasbihModel> {
        val mTasbihModel: MutableList<TasbihModel> = mutableListOf()
        for (i in 0..index.size - 1) {
            mTasbihModel.add(
                TasbihModel(
                    i,
                    tasbihs[i].duaBangla, tasbihs[i].duaEnglish, tasbihs[i].duaArabic,
                    AppPreference.loadTashbihCount(i.toString())
                )
            )
        }
        return mTasbihModel
    }

    @SuppressLint("SetTextI18n")
    override fun getUserCount(count: Int) {
        mLocalCount = 0
        mUserSelectCount = count
        tvTimes.text =
            LanguageConverter.convertToNumber(count.toString()) + " " + getString(R.string.txt_times)
        tvCount.text = getString(R.string.text_zero)
        pbCircle.progress = 0
    }

    override fun getSelectedItem(name: String) {
        selectedItem = name
    }
}