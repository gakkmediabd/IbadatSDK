package com.ibadat.sdk.baseClass

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ibadat.sdk.R
import kotlin.reflect.KProperty


open class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("BF", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        Log.e("BF", "onCreate: ")
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return property.name
    }

    fun initToolbar(view: FragmentActivity): Toolbar {
        val toolbar: Toolbar = view.findViewById(R.id.tb_library)
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white)
        return toolbar
    }
}