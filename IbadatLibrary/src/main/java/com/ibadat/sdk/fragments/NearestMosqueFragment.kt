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
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.NearestMosqueAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.manager.FetchNetworkData
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.nearby.NearPlaceResult
import com.ibadat.sdk.data.model.nearby.PlaceInfo
import com.ibadat.sdk.data.view_models.NearbySharedViewModel
import com.ibadat.sdk.data.restrepo.NetworkDataCallBack
import com.ibadat.sdk.util.PermissionManager
import com.ibadat.sdk.views.MyCustomTextView
import java.io.Serializable


internal class NearestMosqueFragment : BaseFragment(), DistanceControl, NetworkDataCallBack {
    @Transient
    var placeInfoList = arrayListOf<PlaceInfo>()

    @Transient
    private var markerOptions: Array<MarkerOptions>? = null

    @Transient
    private var bitmapDescriptor: BitmapDescriptor? = null

//    @Transient
//    private lateinit var repository: RestRepo

//    @Transient
//    private lateinit var model: NearbyViewModel

    @Transient
    private lateinit var sharedViewModel: NearbySharedViewModel

    @Transient
    private lateinit var adapter: NearestMosqueAdapter

    private val REQUEST_LOCATION = 1

    private lateinit var cvDistanceRange: CardView
    private lateinit var ctvOneKm: MyCustomTextView
    private lateinit var ctvTwoKm: MyCustomTextView
    private lateinit var ctvThreeKm: MyCustomTextView
    private lateinit var rvMosque: RecyclerView
    internal lateinit var view: View

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
        view = inflater.inflate(R.layout.fragment_nearest_mosque, container, false)
        cvDistanceRange = view.findViewById(R.id.cv_distance_range)
        ctvOneKm = view.findViewById(R.id.ctv_one_km)
        ctvTwoKm = view.findViewById(R.id.ctv_two_km)
        ctvThreeKm = view.findViewById(R.id.ctv_three_km)
        rvMosque = view.findViewById(R.id.rv_mosque)

        val resource = R.drawable.mosq
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(resource)
        FetchNetworkData.fetchNearbyPlace("5000", this)
        sharedViewModel = ViewModelProvider(requireActivity())[NearbySharedViewModel::class.java]
        checkPermission()
        return view
    }

    private fun setNearPlace(nearPlaceResult: MutableList<NearPlaceResult>) {
        Log.e("NMF", "setNearPlace: " + nearPlaceResult.size)
        val nearPlaceResultList: List<NearPlaceResult> = nearPlaceResult
        markerOptions = Array(nearPlaceResultList.size) { MarkerOptions() }
        for (i in 0..nearPlaceResultList.size - 1) {
            val mNearPlace: NearPlaceResult = nearPlaceResultList[i]
            val placeInfo = PlaceInfo()
            placeInfo.name = mNearPlace.name
            placeInfo.address = mNearPlace.vicinity
            placeInfo.placeLocation = mNearPlace.geometry?.location
            placeInfoList.add(placeInfo)

            markerOptions!![i] = MarkerOptions()
                .position(
                    LatLng(
                        mNearPlace.geometry?.location
                            ?.lat!!,
                        mNearPlace.geometry?.location
                            ?.lng!!
                    )
                )
                .title(mNearPlace.name)
                .snippet(mNearPlace.name)
                .icon(bitmapDescriptor)
        }

        if (!this@NearestMosqueFragment::adapter.isInitialized) {

            rvMosque.visibility = View.VISIBLE

            adapter = NearestMosqueAdapter(
                requireContext(),
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
                }
            }
            rvMosque.adapter = adapter
        } else {
            adapter.updatePlaceInfo(placeInfoList)
            adapter.notifyDataSetChanged()
            rvMosque.visibility = View.VISIBLE
        }
        rvMosque.layoutManager = LinearLayoutManager(requireContext())
        sharedViewModel.shareMarkerOptions(markerOptions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        getDataList("5000")
        ctvTwoKm.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.apps_color
            )
        )
        ctvTwoKm.setTextColor(Color.WHITE)
        ctvOneKm.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.back_loc
            )
        )
        ctvOneKm.setTextColor(ContextCompat.getColor(requireContext(), R.color.apps_color))
        ctvThreeKm.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.back_loc
            )
        )
        ctvThreeKm.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.apps_color
            )
        )
        ctvOneKm.setOnClickListener {
            // sharedViewModel.setRange(1)
            ctvOneKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            ctvOneKm.setTextColor(Color.WHITE)
            ctvTwoKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            ctvTwoKm.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            ctvThreeKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            ctvThreeKm.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            updateDistance(1)
        }
        ctvTwoKm.setOnClickListener {
            // sharedViewModel.setRange(1)
            ctvTwoKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            ctvTwoKm.setTextColor(Color.WHITE)
            ctvOneKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            ctvOneKm.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            ctvThreeKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            ctvThreeKm.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            updateDistance(7)
        }
        ctvThreeKm.setOnClickListener {
            // sharedViewModel.setRange(1)
            ctvThreeKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            ctvThreeKm.setTextColor(Color.WHITE)
            ctvOneKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            ctvOneKm.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.apps_color
                )
            )
            ctvTwoKm.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.back_loc
                )
            )
            ctvTwoKm.setTextColor(
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
        FetchNetworkData.fetchNearbyPlace(radius, this)
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

    override fun onLoading(loading: Boolean) {
    }

    override fun onSuccess(response: Any) {
        val listNearPlace: MutableList<NearPlaceResult> = response as MutableList<NearPlaceResult>
        setNearPlace(listNearPlace)
    }

    override fun onError(errorMessage: String) {
    }
}

interface DistanceControl : Serializable {
    fun updateDistance(value: Int)
    fun getList(): Array<MarkerOptions>?
}