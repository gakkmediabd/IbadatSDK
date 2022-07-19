package com.ibadat.sdk.adapter

import android.content.Context
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

internal class DuaRVAdapter(val context: Context, private val callBack: CallBack) :
    RecyclerView.Adapter<DuaRVAdapter.DuaViewHolder>() {
    private var mDuaList: MutableList<CommonDuaAndHadithModel>

    init {
        mDuaList = mutableListOf()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DuaViewHolder {
        return DuaViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.dua_list_item, parent, false)
        )
    }

    fun setDuaData(commonDuaAndHadiths: MutableList<CommonDuaAndHadithModel>) {
        mDuaList = commonDuaAndHadiths
    }

    override fun onBindViewHolder(
        holder: DuaViewHolder,
        position: Int
    ) {
        holder.onBind(position)
        val duaM = mDuaList[position]
        holder.itemView.setOnClickListener {
            callBack.onItemClick(mDuaList, position)
        }
    }

    override fun getItemCount(): Int {
        return mDuaList.size
    }

    inner class DuaViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var ctvCount = itemView.findViewById<MyCustomTextView>(R.id.ctv_count)
        private var ctvTitle = itemView.findViewById<MyCustomTextView>(R.id.ctv_title)
        private var imgArt = itemView.findViewById<ImageView>(R.id.imageArt)
        override fun onBind(position: Int) {
            super.onBind(position)
            val mDua = mDuaList[position]
            if (!EmptyCheck.isEmpty(mDua.getSerial())) {
                ctvCount.text =
                    LanguageConverter.getDateInBangla((mDua.getSerial()))
            } else {

                ctvCount.text = LanguageConverter.getDateInBangla((mDua.getId()))
            }
            ctvTitle.text = mDua.getTitle()
            imgArt.setImageURI(
                Util.getUriFromPath(
                    context,
                    AppConstantUtils.drawable_hdpi + "art.png"
                )
            )
        }
    }
}