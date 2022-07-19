package com.ibadat.sdk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.QuranDetailsModel
import com.ibadat.sdk.data.model.islamicholidays.IslamicHolidayListResponse

internal class SurahDetailsAdapter(
    var context: Context,
    var ayatList: List<QuranDetailsModel.Data>?
) :
    RecyclerView.Adapter<SurahDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.quran_details_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: QuranDetailsModel.Data? = ayatList?.get(index = position)
        holder.txtAyatMal.text = model?.textInArabic
        holder.txtMeaningMal.text = model?.text
    }

    override fun getItemCount(): Int {
        return ayatList!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        val txtAyatMal: TextView
        val txtMeaningMal: TextView

        init {
            txtAyatMal = mView.findViewById(R.id.txtAyatMal)
            txtMeaningMal = mView.findViewById(R.id.txtMeaningMal)
        }
    }
}