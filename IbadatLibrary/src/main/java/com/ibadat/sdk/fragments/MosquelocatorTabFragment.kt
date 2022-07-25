package com.ibadat.sdk.fragments

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.ViewPagerAdapter
import com.ibadat.sdk.baseClass.BaseFragment


internal class MosquelocatorTabFragment : BaseFragment() {
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_mosquelocator_tab, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.mosque_on)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.map_on)
        val adapter = context?.let {
            ViewPagerAdapter(
                it, childFragmentManager,
                tabLayout.tabCount
            )
        }
        pager.adapter = adapter
        tabLayout.getTabAt(0)?.setIcon(R.drawable.mosque_on).apply {
            setTintDrawable(this?.icon)
        }
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
                when (pager.currentItem) {
                    0 -> {
                        tabLayout.getTabAt(0)?.setIcon(R.drawable.mosque_on).apply {
                            setTintDrawable(this?.icon)
                        }
                        tabLayout.getTabAt(1)?.setIcon(R.drawable.map_on)
                    }
                    1 -> {

                        tabLayout.getTabAt(0)?.setIcon(R.drawable.mosque_on)
                        tabLayout.getTabAt(1)?.setIcon(R.drawable.map_on).apply {
                            setTintDrawable(this?.icon)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        pager.offscreenPageLimit = 2
    }

    private fun setTintDrawable(drIcon: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drIcon?.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }
}