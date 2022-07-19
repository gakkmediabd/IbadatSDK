package com.ibadat.sdk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.view_holder.BaseViewHolder
import com.ibadat.sdk.data.model.roza.IfterAndSehriTime
import com.ibadat.sdk.roza.TimeFormtter
import com.ibadat.sdk.util.AppPrefUtils
import com.ibadat.sdk.views.MyCustomTextView

internal class RojaRVAdapter : RecyclerView.Adapter<RojaRVAdapter.RojaViewHolder>() {
    private var mRojaList: MutableList<IfterAndSehriTime>
    init {
        mRojaList = mutableListOf()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RojaViewHolder {
        return RojaViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.roja_date_time_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RojaViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setRojaTimes(listRojaTimes: MutableList<IfterAndSehriTime>) {
        mRojaList = listRojaTimes
    }

    override fun getItemCount(): Int {
        return mRojaList.size
    }

    inner class RojaViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var ctvDate: MyCustomTextView = itemView.findViewById(R.id.ctv_date)
        private var ctvSahriTime: MyCustomTextView = itemView.findViewById(R.id.ctv_sahri_time)
        private var ctvIftarTime: MyCustomTextView = itemView.findViewById(R.id.ctv_iftar_time)

        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            super.onBind(position)
            val mRoja = mRojaList[position]
            ctvDate.text =
                mRoja.dayOfGeorgianMonth + " " + AppPrefUtils.getBanglaMonthNameByMonthNumber(
                    TimeFormtter.getMMFormattedStringFromMS(
                        mRoja.dateMs
                    ).toInt()
                )
            ctvSahriTime.text = mRoja.sehriTimeStr
            ctvIftarTime.text = mRoja.ifterTimeStr
        }
    }
}