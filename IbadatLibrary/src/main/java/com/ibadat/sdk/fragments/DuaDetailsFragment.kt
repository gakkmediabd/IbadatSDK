package com.ibadat.sdk.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.gson.reflect.TypeToken
import com.ibadat.sdk.IbadatSdkCore
import com.ibadat.sdk.R
import com.ibadat.sdk.activities.HomeIbadatSdkActivity
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.model.CommonDuaAndHadithModel
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.views.MyCustomTextView


internal class DuaDetailsFragment : BaseFragment() {
    private lateinit var ctvNumber: MyCustomTextView
    private lateinit var ctvTitle: MyCustomTextView
    private lateinit var ctvDua: MyCustomTextView
    private lateinit var ctvPronunciation: MyCustomTextView
    private lateinit var ctvMeaning: MyCustomTextView
    private lateinit var ctvFazilat: MyCustomTextView
    private lateinit var imageView: ImageView
    private lateinit var next: ConstraintLayout
    private lateinit var previous: ConstraintLayout
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dua_details, container, false)
        navController = findNavController()
        viewInitialize(view)
        getDuaModelBundle()
        return view
    }

    private fun getDuaModelBundle() {
        if (AppConstantUtils.requestTypeValue == IbadatSdkCore.DUA) {
            val type = object : TypeToken<List<CommonDuaAndHadithModel>>() {}.type
            val duaModels = AppConstantUtils.getAnyObjectFromJsonString(
                AppConstantUtils.parameterPass,
                type
            ) as List<CommonDuaAndHadithModel>
            var position = AppConstantUtils.parameterPassIndex
            next.setOnClickListener {
                var mSuraIndex: Int
                position = if (position.also { mSuraIndex = it } >= 34) 0 else mSuraIndex + 1
                setDuaData(duaModels[position])
            }
            previous.setOnClickListener {
                if (position == 0) {
                    position = position
                } else {
                    if (position == position) {
                        position = position - 1
                    }
                    setDuaData(duaModels[position])
                }
            }
            setDuaData(duaModels[position])
        }
        if (AppConstantUtils.requestTypeValue == IbadatSdkCore.HADITH) {
            val type = object : TypeToken<List<CommonDuaAndHadithModel>>() {}.type
            val hadithModels =
                AppConstantUtils.getAnyObjectFromJsonString(
                    AppConstantUtils.parameterPass,
                    type
                ) as List<CommonDuaAndHadithModel>
            var position = AppConstantUtils.parameterPassIndex
            next.setOnClickListener {
                var mSuraIndex: Int
                position = if (position.also { mSuraIndex = it } >= 99) 0 else mSuraIndex + 1
                setHadithData(hadithModels[position])
            }
            previous.setOnClickListener {
                if (position == 0) {
                    position = position
                } else {
                    if (position == position) {
                        position = position - 1
                    }
                    setHadithData(hadithModels[position])
                }
            }
            setHadithData(hadithModels[position])
        }
    }

    private fun viewInitialize(view: View) {
        ctvNumber = view.findViewById(R.id.ctv_number)
        ctvTitle = view.findViewById(R.id.ctv_title)
        ctvDua = view.findViewById(R.id.ctv_dua)
        ctvPronunciation = view.findViewById(R.id.ctv_pronunciation)
        ctvMeaning = view.findViewById(R.id.ctv_meaning)
        ctvFazilat = view.findViewById(R.id.ctv_fazilat)
        next = view.findViewById(R.id.nextActionContent)
        previous = view.findViewById(R.id.layoutPrevActionContent)
        imageView = view.findViewById(R.id.imageArt)
        imageView.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "art.png"
            )
        )
        initToolbar(requireActivity())
            .setNavigationOnClickListener {
                if (HomeIbadatSdkActivity.backPressCount == 1) {
                    HomeIbadatSdkActivity.backPressCount -= 1
                    navController.popBackStack()
                }
            }
    }

    private fun setDuaData(commonDuaAndHadithModel: CommonDuaAndHadithModel) {
        ctvNumber.text = LanguageConverter.getDateInBangla(commonDuaAndHadithModel.getSerial())
        ctvTitle.text = commonDuaAndHadithModel.getTitle()
        ctvDua.text = commonDuaAndHadithModel.getDua()
        ctvPronunciation.text = commonDuaAndHadithModel.getPronounciation()
        ctvMeaning.text = commonDuaAndHadithModel.getMeaning()
        ctvFazilat.text = commonDuaAndHadithModel.getFazilat()
    }

    @SuppressLint("SetTextI18n")
    private fun setHadithData(commonDuaAndHadithModel: CommonDuaAndHadithModel) {
        ctvNumber.text = LanguageConverter.getDateInBangla(commonDuaAndHadithModel.getSerial())
        ctvTitle.text =
            commonDuaAndHadithModel.getTitle() + "\n" + getString(R.string.hadith_source) + commonDuaAndHadithModel.getSource()
        ctvDua.text = commonDuaAndHadithModel.getNarrator()
        val strWithoutHtml: String =
            Html.fromHtml(commonDuaAndHadithModel.getDescription()).toString()
        ctvPronunciation.text = strWithoutHtml
    }
}