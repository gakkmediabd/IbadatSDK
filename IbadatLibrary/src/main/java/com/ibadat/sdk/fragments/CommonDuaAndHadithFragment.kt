package com.ibadat.sdk.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.IbadatSdkCore
import com.ibadat.sdk.R
import com.ibadat.sdk.activities.HomeIbadatSdkActivity
import com.ibadat.sdk.adapter.DuaRVAdapter
import com.ibadat.sdk.adapter.HadithRVAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.call_back.CallBack
import com.ibadat.sdk.data.manager.FetchNetworkData
import com.ibadat.sdk.data.model.CommonDuaAndHadithModel
import com.ibadat.sdk.data.restrepo.NetworkDataCallBack
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.CustomAnimation

internal class CommonDuaAndHadithFragment : BaseFragment(), NetworkDataCallBack, CallBack {
    private lateinit var rvCommonDuaOrHadith: RecyclerView
    private lateinit var mDuaAdapter: DuaRVAdapter
    private lateinit var mHadithAdapter: HadithRVAdapter
    private lateinit var pbDuaLoader: ProgressBar
    private lateinit var tvMessage: TextView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dua, container, false)
        navController = findNavController()
        viewInitialize(view)
        return view
    }

    private fun viewInitialize(view: View) {
        pbDuaLoader = view.findViewById(R.id.pb_dua_loader)
        tvMessage = view.findViewById(R.id.tv_message)
        rvCommonDuaOrHadith = view.findViewById(R.id.rv_pray_list)
    }

    override fun onResume() {
        super.onResume()
        initToolbar(requireActivity())
            .setNavigationOnClickListener {
                if (HomeIbadatSdkActivity.backPressCount == 0) {
                    requireActivity().finish()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pbDuaLoader.visibility = View.VISIBLE
        mDuaAdapter = DuaRVAdapter(requireContext(), this)
        mHadithAdapter = HadithRVAdapter(requireContext(), this)

        if (AppConstantUtils.requestTypeValue == IbadatSdkCore.DUA) {
            FetchNetworkData.fetchDua(this)
        }
        if (AppConstantUtils.requestTypeValue == IbadatSdkCore.HADITH) {
            FetchNetworkData.fetchHadith(this)
        }
    }

    private fun loadDuaData(dualist: MutableList<CommonDuaAndHadithModel>) {
        mDuaAdapter.setDuaData(dualist)
        rvCommonDuaOrHadith.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = mDuaAdapter
        }
    }

    private fun loadHadithData(
        dualist: MutableList<CommonDuaAndHadithModel>
    ) {
        mHadithAdapter.setHadithData(dualist)
        rvCommonDuaOrHadith.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = CustomAnimation.getAnimatedRecyclerView(mHadithAdapter)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun hideMessage() {
        tvMessage.visibility = View.VISIBLE
        tvMessage.text = "Data are empty"
    }

    override fun onLoading(loading: Boolean) {
        pbDuaLoader.visibility = View.GONE
    }

    override fun onSuccess(response: Any) {
        val listDuaOrHadith = response as MutableList<CommonDuaAndHadithModel>
        if (listDuaOrHadith.size > 0) {
            if (AppConstantUtils.requestTypeValue == IbadatSdkCore.DUA) {
                loadDuaData(listDuaOrHadith)
            }
            if (AppConstantUtils.requestTypeValue == IbadatSdkCore.HADITH) {
                loadHadithData(listDuaOrHadith)
            }
        } else {
            hideMessage()
        }
    }

    override fun onError(errorMessage: String) {
        hideMessage()
    }


    override fun onItemClick(mDuaList: MutableList<CommonDuaAndHadithModel>, selectedIndex: Int) {
        val duaOrHadithJsonStr = AppConstantUtils.getJsonString(mDuaList)
        if (AppConstantUtils.requestTypeValue == IbadatSdkCore.DUA) {
            HomeIbadatSdkActivity.backPressCount += 1
            navController.navigate(R.id.action_commonDuaAndHadithFragment_to_duaDetailsFragment)
            AppConstantUtils.parameterPass = duaOrHadithJsonStr
            AppConstantUtils.parameterPassIndex = selectedIndex
            AppConstantUtils.duaModel = mDuaList.toMutableList().toString()
        }
        if (AppConstantUtils.requestTypeValue == IbadatSdkCore.HADITH) {
            HomeIbadatSdkActivity.backPressCount += 1
            navController.navigate(R.id.action_commonDuaAndHadithFragment_to_duaDetailsFragment)
            AppConstantUtils.parameterPass = duaOrHadithJsonStr
            AppConstantUtils.parameterPassIndex = selectedIndex
        }
    }
}