package com.ibadat.sdk.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.SurahListAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.util.CustomAnimation


internal class SurahListFragment : BaseFragment(), CallBackSurah {
    private lateinit var rvCommonDuaOrHadith: RecyclerView
    private lateinit var mSurahAdapter: SurahListAdapter
    private lateinit var pbDuaLoader: ProgressBar
    private lateinit var tvMessage: TextView

    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_surah, container, false)
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCommonDuaOrHadith.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            mSurahAdapter = SurahListAdapter(requireContext(), this@SurahListFragment)
            adapter = CustomAnimation.getAnimatedRecyclerView(mSurahAdapter)
        }
    }

    override fun onItemClick(item: Int) {
        val bundle = Bundle()
        bundle.putInt("position", item)
        findNavController().navigate(R.id.action_surahFragment_to_surahDetailsFragment, bundle)
    }
}

interface CallBackSurah {
    fun onItemClick(item: Int)
}


