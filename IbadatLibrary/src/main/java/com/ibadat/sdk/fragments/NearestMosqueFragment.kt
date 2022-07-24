package com.ibadat.sdk.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ibadat.sdk.BuildConfig
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.NearestMosqueAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.nearby.PlaceInfo
import com.ibadat.sdk.data.restrepo.*
import com.ibadat.sdk.databinding.FragmentNearestMosqueBinding
import com.ibadat.sdk.util.PermissionManager
import kotlinx.coroutines.launch
import java.io.Serializable


internal class NearestMosqueFragment : BaseFragment(), DistanceControl {
    @Transient
    private lateinit var binding: FragmentNearestMosqueBinding

    @Transient
    var placeInfoList = arrayListOf<PlaceInfo>()

    @Transient
    private var markerOptions: Array<MarkerOptions>? = null

    @Transient
    private var bitmapDescriptor: BitmapDescriptor? = null

    @Transient
    private lateinit var repository: RestRepo

    @Transient
    private lateinit var model: NearbyViewModel

    @Transient
    private lateinit var sharedViewModel: NearbySharedViewModel

    @Transient
    private lateinit var adapter: NearestMosqueAdapter

    private val REQUEST_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(requireContext())
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycleScope.launch {
            binding =
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_nearest_mosque,
                    container,
                    false
                )
            val resource = R.drawable.mosq
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(resource)

            val job = launch {
                repository = RetroClient2.getRepository()
            }
            job.join()
            model = ViewModelProviders.of(
                this@NearestMosqueFragment,
                NearbyViewModel.FACTORY(repository)
            ).get(NearbyViewModel::class.java)

            sharedViewModel =
                ViewModelProvider(requireActivity())[NearbySharedViewModel::class.java]
            checkPermission()

            model.nearbyInfo.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                    }

                    Status.SUCCESS -> {
                        val resultList: List<com.ibadat.sdk.data.model.nearby.Result> =
                            it.data?.results!!
                        markerOptions = Array(resultList.size) { MarkerOptions() }
                        for (i in 0..resultList.size - 1) {
                            val result: com.ibadat.sdk.data.model.nearby.Result = resultList[i]
                            val placeInfo = PlaceInfo()
                            placeInfo.name = result.name
                            placeInfo.address = result.vicinity
                            placeInfo.placeLocation = result.geometry?.location
                            placeInfoList.add(placeInfo)

                            markerOptions!![i] = MarkerOptions()
                                .position(
                                    LatLng(
                                        result.geometry?.location
                                            ?.lat!!,
                                        result.geometry?.location
                                            ?.lng!!
                                    )
                                )
                                .title(result.name)
                                .snippet(result.name)
                                .icon(bitmapDescriptor)
                        }

                        if (!this@NearestMosqueFragment::adapter.isInitialized) {

                            binding.rvMosque.visibility = View.VISIBLE

                            adapter = NearestMosqueAdapter(
                                placeInfoList = placeInfoList
                            ).apply {
                                setOnItemClickListener { pi ->
                                    if (ActivityCompat.checkSelfPermission(
                                           requireContext(),
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                        ) !== PackageManager.PERMISSION_GRANTED &&
                                        ActivityCompat.checkSelfPermission(
                                           requireContext(),
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        ) !== PackageManager.PERMISSION_GRANTED
                                    ) {
                                        requestPermissions(
                                           requireActivity(), arrayOf(
                                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                                Manifest.permission.ACCESS_FINE_LOCATION
                                            ),
                                            REQUEST_LOCATION
                                        )
                                    } else {
                                        openLocationInMap(pi)
                                        Log.e("DB", "PERMISSION GRANTED")
                                    }
//                                    if (PermissionManager.isLocationPermissionGiven(requireContext())) {
//                                        openLocationInMap(pi)
//                                    } else {
//                                       PermissionManager.requestPermissionForLocation(
//                                            requireActivity()
//                                      ) {
//                                            openLocationInMap(pi)
//                                       }
////
//                                    }
                                }
                            }
                            binding.rvMosque.adapter = adapter
                        } else {
                            adapter.updatePlaceInfo(placeInfoList)
                            adapter.notifyDataSetChanged()
                            binding.rvMosque.visibility = View.VISIBLE
                        }
                        binding.rvMosque.layoutManager = LinearLayoutManager(requireContext())
                        sharedViewModel.shareMarkerOptions(markerOptions)
                    }
                    Status.ERROR -> {
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        getDataList("5000")
        binding.twoKmText.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.apps_color
            )
        )
        binding.twoKmText.setTextColor(Color.WHITE)

        binding.oneKmText.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.back_loc
            )
        )
        binding.oneKmText.setTextColor(ContextCompat.getColor(requireContext(), R.color.apps_color))
        binding.threeKmText.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.back_loc
            )
        )
        binding.threeKmText.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.apps_color
            )
        )

        binding.oneKmText.setOnClickListener {
            // sharedViewModel.setRange(1)
            binding.oneKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            binding.oneKmText.setTextColor(Color.WHITE)
            binding.twoKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            binding.twoKmText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            binding.threeKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            binding.threeKmText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            updateDistance(1)
        }

        binding.twoKmText.setOnClickListener {
            // sharedViewModel.setRange(1)
            binding.twoKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            binding.twoKmText.setTextColor(Color.WHITE)
            binding.oneKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            binding.oneKmText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            binding.threeKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            binding.threeKmText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            updateDistance(7)
        }
        binding.threeKmText.setOnClickListener {
            // sharedViewModel.setRange(1)
            binding.threeKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            binding.threeKmText.setTextColor(Color.WHITE)
            binding.oneKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            binding.oneKmText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            binding.twoKmText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            binding.twoKmText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            updateDistance(10)
        }
    }

    private fun openLocationInMap(pi: PlaceInfo?) {
        pi?.let {
            try {
                val uri =
                    "http://maps.google.com/maps?saddr=" + AppPreference.getUserCurrentLocation().lat + "," + AppPreference.getUserCurrentLocation().lng + "&daddr=" + pi.placeLocation?.lat + "," + pi.placeLocation?.lng
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                requireActivity().startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun checkPermission() {
        if (!PermissionManager.isLocationPermissionGiven(requireContext())) {
//            PermissionManager.requestPermissionForLocation(
//                requireActivity()
//            )
            requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_LOCATION
            )
        }
    }

    private fun getDataList(radius: String) {
        model.loadNearbyPlaceInfo(
            BuildConfig.MAP_API_KEY,
            radius,
            AppPreference.getUserCurrentLocation(),
            "mosque",
            "bn"
        )
    }

    override fun getList(): Array<MarkerOptions>? {
        return markerOptions
    }

    override fun updateDistance(value: Int) {
        placeInfoList.clear()
        when (value) {
            7 -> {
                getDataList("5000")
            }
            10 -> {
                getDataList("10000")
            }
            1 -> {
                getDataList("1000")
            }
        }
    }
}

interface DistanceControl : Serializable {
    fun updateDistance(value: Int)
    fun getList(): Array<MarkerOptions>?
}