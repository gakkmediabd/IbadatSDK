package com.ibadat.sdk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.islamicholidays.IslamicHolidayListResponse
import com.ibadat.sdk.views.MyCustomTextView


internal class IslamicChhutiAdapter(arrayList: List<IslamicHolidayListResponse.Data>?) :
    RecyclerView.Adapter<IslamicChhutiAdapter.ViewHolder>() {

    private var listItemClickListener: ListItemClickListener? = null
    private val mDataList: List<IslamicHolidayListResponse.Data>? = arrayList

    interface ListItemClickListener {
        fun onItemClick(i: Int, view: View?)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.item_islamic_chuti, viewGroup, false), listItemClickListener
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.ctvDate.text = mDataList!![i].text
        viewHolder.ctvTitle.text = mDataList[i].title
    }

    fun setOnItemClickListener(listItemClickListener2: ListItemClickListener?) {
        listItemClickListener = listItemClickListener2
    }

    override fun getItemCount(): Int {
        return mDataList!!.size
    }

    inner class ViewHolder(
        private val viewItem: View,
        private val listItemClickListener: ListItemClickListener?
    ) : RecyclerView.ViewHolder(viewItem), View.OnClickListener {
        val ctvTitle: MyCustomTextView = itemView.findViewById(R.id.ctv_title)
        val ctvDate: MyCustomTextView = itemView.findViewById(R.id.ctv_date)

        init {
            viewItem.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val listItemClickListener2 = listItemClickListener
            listItemClickListener2?.onItemClick(layoutPosition, viewItem)
        }
    }
}