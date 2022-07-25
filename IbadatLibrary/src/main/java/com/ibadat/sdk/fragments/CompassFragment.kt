package com.ibadat.sdk.fragments


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.ibadat.sdk.R
import com.ibadat.sdk.activities.HomeIbadatSdkActivity
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.MAKKAH_LATITUDE
import com.ibadat.sdk.util.MAKKAH_LONGITUDE
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.views.MyCustomTextView
import kotlinx.android.synthetic.main.fragment_compass.*
import java.text.DecimalFormat

internal class CompassFragment : BaseFragment(), SensorEventListener {
    lateinit var toolbar: Toolbar
    private lateinit var mSensorManager: SensorManager
    var userLocation: Array<Double>? = null
    val makkahLocation = arrayOf(MAKKAH_LATITUDE, MAKKAH_LONGITUDE)
    var bearing: Double? = null
    private var tvDistance: MyCustomTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_compass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val constraintImage = requireView().findViewById<ImageView>(R.id.constraintImage)
        constraintImage.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "compass_bg.png"
            )
        )
        appCompatImageView.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "dial.png"
            )
        )
        imgkabba.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "kaaba_icon_with_arrow.png"
            )
        )
        initActionBar()
        val distance = getDistance(
            AppPreference.getUserCurrentLocation().lat!!,
            AppPreference.getUserCurrentLocation().lng!!,
            MAKKAH_LATITUDE,
            MAKKAH_LONGITUDE
        ).toDouble()
        val distanceOfMakkah =
            context?.resources!!.getString(R.string.makkah_distance) + "  " + DecimalFormat("##.##").format(
                distance / 1000
            ) + "  " + context?.resources!!.getString(R.string.km_text)
        tvDistance = requireActivity().findViewById(R.id.tvDistance)
        tvDistance?.text = distanceOfMakkah
        userLocation = arrayOf(
            AppPreference.getUserCurrentLocation().lat!!,
            AppPreference.getUserCurrentLocation().lng!!
        )
        val d = userLocation?.let { getBearingBetweenTwoPoints(it, makkahLocation) }
        if (d != null) {
            bearing = if (d > 0) {
                d
            } else {
                360 + d
            }
        }
    }

    private fun initActionBar() {
        toolbar = requireActivity().findViewById(R.id.tb_library)
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white)
        toolbar.setNavigationOnClickListener {
            if (HomeIbadatSdkActivity.backPressCount == 0) {
                requireActivity().finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        val mSensor = mSensorManager.getDefaultSensor(3)
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, 1)
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val layoutCompass: ConstraintLayout = requireActivity().findViewById(R.id.layoutCompass)
        val kaabaView: AppCompatImageView = requireActivity().findViewById(R.id.imgkabba)
        val degree = Math.round(sensorEvent?.values!!.get(0))
        layoutCompass.rotation = -degree.toFloat()

        kaabaView.rotation = bearing?.toFloat()!!
        val tvLocationCompass =
            requireActivity().findViewById<MyCustomTextView>(R.id.tvLocationCompass)
        val degreeTxt =
            degree.toString() + 0x00B0.toChar() + " " + context?.getString(R.string.degree_txt)
        tvLocationCompass.text = degreeTxt
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    fun getDistance(lat_a: Double, lng_a: Double, lat_b: Double, lng_b: Double): Int {
        var miter = 0
        try {
            val earthRadius = 3958.75
            val latDiff = Math.toRadians(lat_b - lat_a)
            val lngDiff = Math.toRadians(lng_b - lng_a)
            val a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                    Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                    Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            val distance = earthRadius * c
            val meterConversion = 1609
            miter = (distance * meterConversion.toFloat().toInt()).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return miter
    }

    private fun degreesToRadians(degrees: Double): Double {
        return degrees * (3.1416 / 180.0)
    }

    private fun radiansToDegrees(radians: Double): Double {
        return radians * (180.0 / 3.1416)
    }

    private fun getBearingBetweenTwoPoints(to: Array<Double>, from: Array<Double>): Double {
        val lat1 = degreesToRadians(degrees = to[0])
        val lon1 = degreesToRadians(degrees = to[1])
        val lat2 = degreesToRadians(degrees = from[0])
        val lon2 = degreesToRadians(degrees = from[1])
        val dLon = lon2 - lon1
        val y = Math.sin(dLon) * Math.cos(lat2)
        val x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon)
        val radiansBearing = Math.atan2(y, x)
        return radiansToDegrees(radiansBearing)
    }
}