package com.ibadat.sdk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.calender.IslamicCalendarModel
import com.ibadat.sdk.databinding.ItemIslamicCalendarBinding
import com.ibadat.sdk.roza.TimeFormtter
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

        val binding: ItemIslamicCalendarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_islamic_calendar, viewGroup, false
        )

        return ViewHolder(
            binding,
            listItemClickListener
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.binding.txtVw1.text = LanguageConverter.getNumberByLocale(mDataList[i].dayTxt)
        if (mDataList[i].isToday) {
            viewHolder.binding.txtVw1.setTextColor(ContextCompat.getColor(viewHolder.binding.root.context, R.color.white))
            viewHolder.binding.llBg.show()
        } else {
            viewHolder.binding.txtVw1.setTextColor(ContextCompat.getColor(viewHolder.binding.root.context, R.color.txt_color_black))
            viewHolder.binding.llBg.invisible()
        }
    }

    fun setOnItemClickListener(listItemClickListener2: ListItemClickListener?) {
        listItemClickListener = listItemClickListener2
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }


    class ViewHolder(
        val binding: ItemIslamicCalendarBinding,
        private val listItemClickListener: ListItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        override fun onClick(view: View?) {
            val listItemClickListener2 = listItemClickListener
            listItemClickListener2?.onItemClick(layoutPosition, binding.root)
        }

        init {
            binding.root.setOnClickListener(this)
        }
    }

}