package com.ibadat.sdk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.islamicholidays.IslamicHolidayListResponse
import com.ibadat.sdk.databinding.ItemIslamicChutiBinding



internal class IslamicChhutiAdapter(arrayList: List<IslamicHolidayListResponse.Data>?) :
    RecyclerView.Adapter<IslamicChhutiAdapter.ViewHolder>() {

    private var listItemClickListener: ListItemClickListener? = null
    private val mDataList: List<IslamicHolidayListResponse.Data>? = arrayList

    interface ListItemClickListener {
        fun onItemClick(i: Int, view: View?)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {

        val binding: ItemIslamicChutiBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context), R.layout.item_islamic_chuti, viewGroup, false
        )

        return ViewHolder(binding, listItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.binding.txtDate.text = mDataList!![i].text
        viewHolder.binding.txtVwTitle.text = mDataList[i].title
    }

    fun setOnItemClickListener(listItemClickListener2: ListItemClickListener?) {
        listItemClickListener = listItemClickListener2
    }

    override fun getItemCount(): Int {
        return mDataList!!.size
    }

    class ViewHolder(
        val binding: ItemIslamicChutiBinding,
        private val listItemClickListener: ListItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val listItemClickListener2 = listItemClickListener
            listItemClickListener2?.onItemClick(layoutPosition, binding.root)
        }
    }


}