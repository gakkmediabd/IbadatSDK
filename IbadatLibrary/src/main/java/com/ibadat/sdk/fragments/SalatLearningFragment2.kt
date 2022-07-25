package com.ibadat.sdk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.NamazShikkaDetailsNewRecyclerViewAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.model.SalatLearningModel
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.util.CustomAnimation
import com.ibadat.sdk.views.MyCustomTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class SalatLearningFragment2 : BaseFragment(), CallBackSalatLearningDetails {
    var recycleCetagory: RecyclerView? = null
    var namazShikhshaTitle: MyCustomTextView? = null
    var txtDescription: MyCustomTextView? = null
    lateinit var progressBar: ProgressBar
    var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_salat_learning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        val bundle = this.arguments

        if (bundle != null) {
            id = bundle.getString("id")
        }
        getNamazShikkhaData2(id)
    }

    private fun initView(view: View) {
        progressBar = view.findViewById<ProgressBar>(R.id.pb_loader)
        recycleCetagory = view.findViewById(R.id.fastCetagory)
        namazShikhshaTitle = view.findViewById(R.id.namaz_shikhsha_title)
        txtDescription = view.findViewById(R.id.txtDescription)
    }

    private fun getNamazShikkhaData2(id: String?) {
        progressBar.visibility = View.VISIBLE
        val api: ApiService = RetroClient.getApiService()!!
        val call = api.getAllINamazShikka(id!!, "bn")
        call.enqueue(object : Callback<List<SalatLearningModel>> {
            override fun onResponse(
                call: Call<List<SalatLearningModel>>,
                response: Response<List<SalatLearningModel>>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    loadRV2(response.body())
                }
            }

            override fun onFailure(call: Call<List<SalatLearningModel>>, t: Throwable) {
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun loadRV2(salatLearningModel: List<SalatLearningModel>?) {
        if (salatLearningModel != null) {
            recycleCetagory?.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val adapter =
                NamazShikkaDetailsNewRecyclerViewAdapter(salatLearningModel, context, this)
            recycleCetagory?.adapter = CustomAnimation.getAnimatedRecyclerView(adapter)
            recycleCetagory?.isNestedScrollingEnabled = false
        }
    }

    override fun onItemclick2(item: Int, id: String?) {
        val bundle = Bundle()
        bundle.putInt("position", item)
        bundle.putString("id", id)
        findNavController().navigate(
            R.id.action_salatLearningFragment2_to_salatLearningDetailsFragment,
            bundle
        )
    }
}

interface CallBackSalatLearningDetails {
    fun onItemclick2(item: Int, id: String?)
}