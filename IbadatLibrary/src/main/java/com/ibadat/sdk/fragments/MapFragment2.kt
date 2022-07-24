package com.ibadat.sdk.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.restrepo.NearbySharedViewModel
import com.ibadat.sdk.data.restrepo.RestRepo
import com.ibadat.sdk.data.manager.prefs.AppPreference

private const val ARG_MOSQUE_CALL_BACK = "mosqueCallBack"

internal class MapFragment2 : BaseFragment(), OnMapReadyCallback {
    @Transient
    private var mMap: GoogleMap? = null

    @Transient
    private var mDistanceControl: DistanceControl? = null

    //    @Transient
//    private lateinit var binding: FragmentMapBinding
    private lateinit var flMap: FrameLayout

    @Transient
    var markerList: Array<MarkerOptions>? = null

    @Transient
    private lateinit var repository: RestRepo

    @Transient
    private lateinit var sharedViewModel: NearbySharedViewModel

    companion object {
        @JvmStatic
        fun newInstance(
            distanceControl: DistanceControl? = null

        ) =
            MapFragment2().apply {
                arguments = Bundle().apply {

                    putSerializable(ARG_MOSQUE_CALL_BACK, distanceControl)

                }
            }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mDistanceControl = it.getSerializable(ARG_MOSQUE_CALL_BACK) as? DistanceControl

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        flMap = view.findViewById(R.id.fl_map)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[NearbySharedViewModel::class.java]

        val options = GoogleMapOptions()
        options.zoomControlsEnabled(true).compassEnabled(true)
        val fragment: SupportMapFragment = SupportMapFragment.newInstance()
        var transaction: FragmentTransaction? = null

        if (fragmentManager != null) {
            transaction = fragmentManager?.beginTransaction()?.replace(R.id.fl_map, fragment)
        }

        transaction?.commit()

        fragment.getMapAsync(this)

//        sharedViewModel.range.observe(viewLifecycleOwner){
//            Log.i("sharedViewModel", "onViewCreated: $it")
//        }
        markLocation()

    }

    private fun markLocation() {

        /* mDistanceControl?.let {
             markerList = mDistanceControl?.getList()
         }*/

        sharedViewModel.markerOptions.observe(viewLifecycleOwner) { markerOptionList ->
            Log.i("sharedViewModel", "markLocation: ${markerOptionList.toString()}")
            if (markerOptionList?.isNotEmpty() == true) {

                for (markerOptions in markerOptionList) {
                    if (mMap != null) {
                        mMap!!.addMarker(markerOptions)
                    } else {
                        return@observe
                    }
                }
                animateCamera()
            }
        }


    }

    private fun animateCamera() {
        val cameraPosition = CameraPosition.Builder()
            .target(
                LatLng(
                    AppPreference.getUserCurrentLocation().lat!!,
                    AppPreference.getUserCurrentLocation().lng!!
                )
            )
            .zoom(17f)
            .build()
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), null)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("NearestMopFrag", "onDestroView: called")
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        markLocation()
    }
}