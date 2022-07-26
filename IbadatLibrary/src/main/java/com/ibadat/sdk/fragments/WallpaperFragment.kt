package com.ibadat.sdk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.WallpaperAnimRecyclerViewAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.model.WallpaperAnimModel
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.util.CustomAnimation
import com.ibadat.sdk.util.ItemOffsetDecoration
import com.ibadat.sdk.util.NetworkCheck
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class WallpaperFragment : BaseFragment() {
    var recycleWallAnimList: RecyclerView? = null
    var categoryType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallpaper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (NetworkCheck.showNetWorkToast(requireContext())) {
            initView()
        }
    }

    private fun initView() {
        recycleWallAnimList = requireView().findViewById(R.id.RecycleWallAnimList)
        categoryType = "wp"
        when (categoryType) {
            "wp" -> loadData("1872L0-Ibad", "1873L1-Wall", "wp")
            "an" -> loadData("1872L0-Ibad", "1874L1-Anim", "an")
            "vd" -> loadData("1872L0-Ibad", "1875L1-Vide", "vd")
            "bft" -> loadData("1872L0-Ibad", "1876L1-Audi", "bft")
            "tt" -> loadData("1893L0-Ibad", "1934L1-Ring", "tt")
        }
    }

    private fun loadData(portalCode: String, categoryCode: String, contentType: String) {
        //Creating an object of our api interface
        val api: ApiService? = RetroClient.getDownloadInfoInstance()
        val call: Call<List<WallpaperAnimModel?>?>? =
            api?.getAllWallpaperAnimation(portalCode, categoryCode, contentType)
        call?.enqueue(object : Callback<List<WallpaperAnimModel?>?> {
            override fun onResponse(
                call: Call<List<WallpaperAnimModel?>?>,
                response: Response<List<WallpaperAnimModel?>?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty()) {
                        setAdapterRecycle(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<List<WallpaperAnimModel?>?>, t: Throwable) {
            }
        })
    }

    private fun setAdapterRecycle(wallpaperAnimModelList: List<WallpaperAnimModel?>?) {
        recycleWallAnimList!!.layoutManager = GridLayoutManager(requireContext(), 2)
        val itemDecoration =
            ItemOffsetDecoration(requireContext(), R.dimen.item_offset1)
        recycleWallAnimList!!.addItemDecoration(itemDecoration)
        recycleWallAnimList!!.adapter = CustomAnimation.getAnimatedRecyclerView(
            WallpaperAnimRecyclerViewAdapter(
                wallpaperAnimModelList,
                requireContext(),
                categoryType!!
            )
        )
        recycleWallAnimList!!.isNestedScrollingEnabled = false
    }
}