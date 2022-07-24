package com.ibadat.sdk.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ibadat.sdk.R
import com.ibadat.sdk.activities.YoutubePlayerActivity
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.model.LiveVideo
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.databinding.FragmentLiveVideoBinding
import com.ibadat.sdk.data.restrepo.RetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class LiveVideoFragment : BaseFragment() {
    private lateinit var ivImageMecca: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_live_video, container, false)
        ivImageMecca = view.findViewById(R.id.iv_image_mecca)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveVideo()

    }

    private fun liveVideo() {
        val api: ApiService = RetroClient.getLiveVideoApiService()!!
        val call = api.getLiveVideo("8801000000000", "bn", "duhar")

        call.enqueue(object : Callback<LiveVideo> {
            override fun onResponse(
                call: Call<LiveVideo>,
                response: Response<LiveVideo>
            ) {
                // progressBar?.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    Log.d(
                        "TAG",
                        "onResponse: image loaded: " + (response.body()?.LiveVideo!![0].PreviewImage)
                    )
                    Glide.with(requireContext()).load(response.body()?.LiveVideo!![0].PreviewImage)
                        .into(ivImageMecca)
                    ivImageMecca.setOnClickListener {
                        val intent = Intent(context, YoutubePlayerActivity::class.java).apply {
                            putExtra("id", response.body()?.LiveVideo!![0].VideoLink)
                        }
                        startActivity(intent)
                    }
                    Glide.with(requireContext())
                        .load(response.body()?.LiveVideo!![1].PreviewImage)
                        .into(ivImageMecca)
                    ivImageMecca.setOnClickListener {
                        val intent = Intent(context, YoutubePlayerActivity::class.java).apply {
                            putExtra("id", response.body()?.LiveVideo!![1].VideoLink)
                        }
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<LiveVideo>, t: Throwable) {
                Log.d(
                    "TAG",
                    "onFailure: " + t.message
                )
            }
        })
    }
}