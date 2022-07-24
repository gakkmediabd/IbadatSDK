package com.ibadat.sdk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.calender.IslamicCalendarModel
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.util.invisible
import com.ibadat.sdk.util.show


internal class IslamicCalendarAdapter(arrayList: ArrayList<IslamicCalendarModel>) :
    RecyclerView.Adapter<IslamicCalendarAdapter.ViewHolder>() {

    private var listItemClickListener: ListItemClickListener? = null
    private val mDataList = arrayList

    interface ListItemClickListener {
        fun onItemClick(i: Int, view: View)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.item_islamic_calendar, viewGroup, false),
            listItemClickListener
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvW1.text = LanguageConverter.getNumberByLocale(mDataList[i].dayTxt)
        if (mDataList[i].isToday) {
            viewHolder.tvW1.setTextColor(
                ContextCompat.getColor(
                    viewHolder.viewItem.context,
                    R.color.white
                )
            )
            viewHolder.llBg.show()
        } else {
            viewHolder.tvW1.setTextColor(
                ContextCompat.getColor(
                    viewHolder.viewItem.context,
                    R.color.txt_color_black
                )
            )
            viewHolder.llBg.invisible()
        }
    }

    fun setOnItemClickListener(listItemClickListener2: ListItemClickListener?) {
        listItemClickListener = listItemClickListener2
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class ViewHolder(
        val viewItem: View,
        private val listItemClickListener: ListItemClickListener?
    ) : RecyclerView.ViewHolder(viewItem), View.OnClickListener {
        val llBg: LinearLayout = itemView.findViewById(R.id.ll_bg)
        val tvW1: TextView = itemView.findViewById(R.id.tv_w1)

        override fun onClick(view: View?) {
            val listItemClickListener2 = listItemClickListener
            listItemClickListener2?.onItemClick(layoutPosition, viewItem)
        }

        init {
            viewItem.setOnClickListener(this)
        }
    }
}