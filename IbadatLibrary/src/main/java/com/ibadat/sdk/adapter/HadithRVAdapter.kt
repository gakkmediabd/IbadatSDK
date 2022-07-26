package com.ibadat.sdk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.view_holder.BaseViewHolder
import com.ibadat.sdk.call_back.CallBack
import com.ibadat.sdk.data.model.CommonDuaAndHadithModel
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.EmptyCheck
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.views.MyCustomTextView

internal class HadithRVAdapter(
    val context: Context,
    private val callBack: CallBack
) :
    RecyclerView.Adapter<HadithRVAdapter.DuaViewHolder>() {
    private var mHadithList: MutableList<CommonDuaAndHadithModel>

    init {
        mHadithList = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DuaViewHolder {
        return DuaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.common_hadith_list_item, parent, false)
        )
    }

    fun setHadithData(hadiths: MutableList<CommonDuaAndHadithModel>) {
        mHadithList = hadiths
    }

    override fun onBindViewHolder(holder: DuaViewHolder, position: Int) {
        holder.onBind(position)
//        val duaM = mHadithList[position]
        holder.itemView.setOnClickListener {
            callBack.onItemClick(mHadithList, position)
        }
    }

    override fun getItemCount(): Int {
        return mHadithList.size
    }

    @SuppressLint("SetTextI18n")
    inner class DuaViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var ctvCount: MyCustomTextView =
            itemView.findViewById(R.id.ctv_count)
        private var ivImageBackground: ImageView = itemView.findViewById(R.id.iv_image_background)
        private var ctvTitle: MyCustomTextView =
            itemView.findViewById(R.id.ctv_title)
        private var ctvDataHeader: MyCustomTextView =
            itemView.findViewById(R.id.ctv_data_header)
        private var ctvData: MyCustomTextView =
            itemView.findViewById(R.id.ctv_data)
        private var imgArt: ImageView = itemView.findViewById(R.id.iv_image_background)

        override fun onBind(position: Int) {
            super.onBind(position)
            val commonDuaAndHadith = mHadithList[position]
            if (!EmptyCheck.isEmpty(commonDuaAndHadith.getSerial())) {
                ctvCount.text =
                    LanguageConverter.getDateInBangla((commonDuaAndHadith.getSerial()))
            } else {
                ctvCount.text = LanguageConverter.getDateInBangla((commonDuaAndHadith.getId()))
            }
            ivImageBackground.setImageURI(
                Util.getUriFromPath(
                    context,
                    AppConstantUtils.drawable_hdpi + "art.png"
                )
            )
            ctvTitle.text = commonDuaAndHadith.getTitle()
            ctvDataHeader.text = context.getString(R.string.hadith_source)
            ctvData.text = commonDuaAndHadith.getSource()
        }
    }
}