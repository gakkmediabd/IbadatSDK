package com.ibadat.sdk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ibadat.sdk.R
import com.ibadat.sdk.fragments.CallBackSurah
import com.ibadat.sdk.util.*
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.views.MyCustomTextView


internal class SurahListAdapter(
    var context: Context? = null, private val callBack: CallBackSurah,
    var requestType: String? = null,
    var QURAN_SURA_ARRAY: Array<String?>? = context?.resources?.getStringArray(R.array.surah_name)
) : RecyclerView.Adapter<SurahListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahListAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.hadith_list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SurahListAdapter.ViewHolder, position: Int) {
        val quranSuraArray = context!!.resources.getStringArray(R.array.surah_name)
        if (!EmptyCheck.isEmpty(quranSuraArray[position])) {
            holder.hadithCount.text = LanguageConverter.getDateInBangla((position + 1).toString())
        } else {
            holder.hadithCount.setText(position)
        }
        holder.hadithTitle.text = quranSuraArray[position]
        holder.txt1.text =
            context!!.getString(R.string.ayat) + " " + LanguageConverter.getDateInBangla(
                GlobalVar.AyetCount[position]
            ) + context!!.getString(R.string.ti)
        holder.itemView.setOnClickListener {
            callBack.onItemClick(position)
        }
        holder.txt2.visibility = View.GONE
    }


    override fun getItemCount(): Int {
        return if (QURAN_SURA_ARRAY == null) 0 else QURAN_SURA_ARRAY!!.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        val hadithCount: MyCustomTextView
        val hadithTitle: MyCustomTextView
        val txt1: MyCustomTextView
        val txt2: MyCustomTextView
        var imageView: ImageView

        init {
            hadithCount = mView.findViewById(R.id.hadithCount)
            hadithTitle = mView.findViewById(R.id.hadith_title)
            txt1 = mView.findViewById(R.id.txt1)
            txt2 = mView.findViewById(R.id.txt2)
            imageView = mView.findViewById(R.id.hadithImageView)
            imageView.setImageURI(
                Util.getUriFromPath(
                    context!!,
                    AppConstantUtils.drawable_hdpi + "art.png"
                )
            )
        }
    }
}