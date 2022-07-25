package com.ibadat.sdk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.model.Jakat.Jakat
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.util.ClickEventCounter
import com.ibadat.sdk.views.MyCustomTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ZakatFragment : BaseFragment() {
    private lateinit var llRomadanRuleDetails1: LinearLayout
    private lateinit var llRomadanRuleDetails2: LinearLayout
    private lateinit var llRomadanRuleDetails3: LinearLayout
    private lateinit var llRomadanRuleDetails4: LinearLayout
    private lateinit var llRomadanRule1: LinearLayout
    private lateinit var llRomadanRule2: LinearLayout
    private lateinit var llRomadanRule3: LinearLayout
    private lateinit var llRomadanRule4: LinearLayout
    private lateinit var jakatHeader1: MyCustomTextView
    private lateinit var jakatHeader2: MyCustomTextView
    private lateinit var jakatHeader3: MyCustomTextView
    private lateinit var jakatHeader4: MyCustomTextView
    lateinit var jakatDescription1: MyCustomTextView
    lateinit var jakatDescription2: MyCustomTextView
    lateinit var jakatDescription3: MyCustomTextView
    lateinit var jakatDescription4: MyCustomTextView
    lateinit var button: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zakat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jakatHeader1 = view.findViewById(R.id.JakatHeader1)
        jakatHeader2 = view.findViewById(R.id.JakatHeader2)
        jakatHeader3 = view.findViewById(R.id.JakatHeader3)
        jakatHeader4 = view.findViewById(R.id.JakatHeader4)
        llRomadanRule1 = view.findViewById(R.id.llRomadan_rule1)
        llRomadanRule2 = view.findViewById(R.id.llRomadan_rule2)
        llRomadanRule3 = view.findViewById(R.id.llRomadan_rule)
        llRomadanRule4 = view.findViewById(R.id.llRomadan_rule4)
        llRomadanRuleDetails1 = view.findViewById(R.id.llRomadan_ruleDetails1)
        llRomadanRuleDetails2 = view.findViewById(R.id.llRomadan_ruleDetails2)
        llRomadanRuleDetails3 = view.findViewById(R.id.llRomadan_ruleDetails)
        llRomadanRuleDetails4 = view.findViewById(R.id.llRomadan_ruleDetails4)
        jakatDescription1 = view.findViewById(R.id.jakatDescription1)
        jakatDescription2 = view.findViewById(R.id.jakatDescription2)
        jakatDescription3 = view.findViewById(R.id.jakatDescription3)
        jakatDescription4 = view.findViewById(R.id.jakatDescription4)
        button = view.findViewById(R.id.btnJakat)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_zakatFragment_to_zakatCalculatorFragment)
        }
        llRomadanRule1.setOnClickListener {
            ClickEventCounter.setCountClickEvent()
            if (llRomadanRuleDetails1.isShown) {
                llRomadanRuleDetails1.visibility = View.GONE
            } else {
                llRomadanRuleDetails1.visibility = View.VISIBLE
            }
        }
        llRomadanRule2.setOnClickListener {
            ClickEventCounter.setCountClickEvent()
            if (llRomadanRuleDetails2.isShown) {
                llRomadanRuleDetails2.visibility = View.GONE
            } else {
                llRomadanRuleDetails2.visibility = View.VISIBLE
            }
        }
        llRomadanRule3.setOnClickListener {
            ClickEventCounter.setCountClickEvent()
            if (llRomadanRuleDetails3.isShown) {
                llRomadanRuleDetails3.visibility = View.GONE
            } else {
                llRomadanRuleDetails3.visibility = View.VISIBLE
            }
        }
        llRomadanRule4.setOnClickListener {
            ClickEventCounter.setCountClickEvent()
            if (llRomadanRuleDetails4.isShown) {
                llRomadanRuleDetails4.visibility = View.GONE
            } else {
                llRomadanRuleDetails4.visibility = View.VISIBLE
            }
        }
        loadJakatData()
        jakatHeader1.text = "যাকাত বন্টনের নির্ধারিত খাত"
        jakatHeader2.text = "যাকাত ফরজ হওয়ার শর্ত "
        jakatHeader3.text = "নিসাব"
        jakatHeader4.text = "যাকাত"
    }

    private fun loadJakatData() {
        val api: ApiService = RetroClient.getJakatApiService()!!
        val call = api.getJakatList("6047013cf255bfe281719874", "undefined", "1")

        call.enqueue(object : Callback<Jakat> {
            override fun onResponse(
                call: Call<Jakat>,
                response: Response<Jakat>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    jakatDescription1.text = response.body()?.data?.get(0)?.text
                    jakatDescription2.text = response.body()?.data?.get(1)?.text
                    jakatDescription3.text = response.body()?.data?.get(2)?.text
                    jakatDescription4.text = response.body()?.data?.get(3)?.text
                }
            }

            override fun onFailure(call: Call<Jakat>, t: Throwable) {
            }
        })
    }
}