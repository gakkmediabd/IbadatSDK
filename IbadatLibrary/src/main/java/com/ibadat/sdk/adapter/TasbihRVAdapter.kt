package com.ibadat.sdk.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.view_holder.BaseViewHolder
import com.ibadat.sdk.call_back.CountControl
import com.ibadat.sdk.data.model.app_model.TasbihModel
import com.ibadat.sdk.util.handleClickEvent
import com.ibadat.sdk.views.TextViewNormal
import com.ibadat.sdk.views.TextViewNormalArabic

internal class TasbihRVAdapter(
    val listTasbihModel: MutableList<TasbihModel>,
    private val mCountControl: CountControl,
    private var viewClickedIndex: Int,
    private var buttonClickedIndex: Int
) :
    RecyclerView.Adapter<TasbihRVAdapter.TasbihViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihViewHolder {
        return TasbihViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.tasbi_count_item_updated, parent, false)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TasbihViewHolder, position: Int) {
        holder.onBind(position)
        when (viewClickedIndex == position) {
            true -> {
                holder.clLayoutParent.setBackgroundResource(R.drawable.border_rounded_green)
            }
            else -> {
                holder.clLayoutParent.setBackgroundResource(R.drawable.rounded_white)
            }
        }

        holder.clLayoutParent.handleClickEvent {
            viewClickedIndex = holder.adapterPosition
            buttonClickedIndex = 0
            mCountControl.getSelectedItem(viewClickedIndex.toString())
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listTasbihModel.size
    }

    inner class TasbihViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var tvReadItemName: TextViewNormal = itemView.findViewById(R.id.tv_read_item_name)
        private var tvReadItemArabicName: TextViewNormalArabic =
            itemView.findViewById(R.id.tv_read_item_arabic_name)
        var clLayoutParent: ConstraintLayout = itemView.findViewById(R.id.cl_layout_parent)

        override fun onBind(position: Int) {
            super.onBind(position)
            val tasbihBAModel = listTasbihModel[position]
            tvReadItemName.text = tasbihBAModel.duaBangla
            tvReadItemArabicName.text = tasbihBAModel.duaArabic
        }
    }
}