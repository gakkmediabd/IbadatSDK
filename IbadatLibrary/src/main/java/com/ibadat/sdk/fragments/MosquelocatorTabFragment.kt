package com.ibadat.sdk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.ViewPagerAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.databinding.FragmentMosquelocatorTabBinding


internal class MosquelocatorTabFragment : BaseFragment() {

    private lateinit var binding:FragmentMosquelocatorTabBinding
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_mosquelocator_tab, container, false)
        return binding.root
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
            this?.icon?.setTint(ContextCompat.getColor(requireContext(),R.color.white))
        }
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
                when (pager.currentItem) {
                    0-> {
                        tabLayout.getTabAt(0)?.setIcon(R.drawable.mosque_on).apply {
                            this?.icon?.setTint(ContextCompat.getColor(requireContext(),R.color.white))
                        }
                        tabLayout.getTabAt(1)?.setIcon(R.drawable.map_on)

                       }
                    1 -> {

                        tabLayout.getTabAt(0)?.setIcon(R.drawable.mosque_on)
                        tabLayout.getTabAt(1)?.setIcon(R.drawable.map_on).apply {
                            this?.icon?.setTint(ContextCompat.getColor(requireContext(),R.color.white))
                        }

                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        pager.offscreenPageLimit=2



    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MosquelocatorTabFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}