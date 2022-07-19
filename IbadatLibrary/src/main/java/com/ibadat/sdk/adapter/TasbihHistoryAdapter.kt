package com.ibadat.sdk.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.view_holder.BaseViewHolder
import com.ibadat.sdk.data.model.app_model.TasbihModel
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.views.TextViewNormal

internal class TasbihHistoryAdapter(val tasbihModel: MutableList<TasbihModel>) :
    RecyclerView.Adapter<TasbihHistoryAdapter.TasbihHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbihHistoryViewHolder {
        return TasbihHistoryViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.tasbih_history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasbihHistoryViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return tasbihModel.size
    }

    inner class TasbihHistoryViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var tvHistoryItemNumber: TextViewNormal =
            itemView.findViewById(R.id.tv_history_item_number)
        private var tvHistoryItemtitle: TextViewNormal =
            itemView.findViewById(R.id.tv_history_item_title)
        private var tvHistoryItemCount: TextViewNormal =
            itemView.findViewById(R.id.tv_history_item_count)

        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            super.onBind(position)
            val tasbihHistoryModel = tasbihModel[position]
            tvHistoryItemNumber.text = LanguageConverter.getNumber(position + 1)
                .let { LanguageConverter.getNumberByLocale(it) }
            tvHistoryItemtitle.text = tasbihHistoryModel.duaBangla
            tvHistoryItemCount.text =
                LanguageConverter.getNumberByLocale(tasbihHistoryModel.duaReadCount.toString()) + " " + tvHistoryItemCount.context.getString(
                    R.string.txt_times
                )
        }
    }
}