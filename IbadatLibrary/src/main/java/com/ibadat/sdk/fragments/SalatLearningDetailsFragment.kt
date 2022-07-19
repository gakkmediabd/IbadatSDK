package com.ibadat.sdk.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.model.SalatLearningModel
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.views.MyCustomTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class SalatLearningDetailsFragment : BaseFragment() {
    lateinit var ctvNamazShikhshaTitle: MyCustomTextView
    lateinit var txtDescription: MyCustomTextView
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.salat_leaarning_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        getNamazShikkaData()
    }

    private fun initView(view: View) {
        ctvNamazShikhshaTitle = view.findViewById(R.id.namaz_shikhsha_title)
        txtDescription = view.findViewById(R.id.txtDescription)
    }

    private fun getNamazShikkaData() {
        val bundle = this.arguments
        if (bundle != null) {
            id = bundle.getString("id")
        }
        val api: ApiService = RetroClient.getApiService()!!
        val call = api.getAllINamazShikkaDescription(id.toString(), "0", "8801000000000", "bn")
        call.enqueue(object : Callback<List<SalatLearningModel>> {
            override fun onResponse(
                call: Call<List<SalatLearningModel>>,
                response: Response<List<SalatLearningModel>>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val strWithoutHtml: String =
                        Html.fromHtml(response.body()!![0].getTopicDescription()).toString()
                    ctvNamazShikhshaTitle.text = response.body()!![0].getTopicName()
                    txtDescription.text = strWithoutHtml
                }
            }

            override fun onFailure(call: Call<List<SalatLearningModel>>, t: Throwable) {
            }
        })
    }
}

