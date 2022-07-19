package com.ibadat.sdk.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ibadat.sdk.fragments.MapFragment2
import com.ibadat.sdk.fragments.NearestMosqueFragment

internal class ViewPagerAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                    NearestMosqueFragment()
               }
            1 -> {
                    MapFragment2.newInstance()
                }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}