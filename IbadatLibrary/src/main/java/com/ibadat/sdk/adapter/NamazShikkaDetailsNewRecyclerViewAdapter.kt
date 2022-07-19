package com.ibadat.sdk.adapter

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
import com.ibadat.sdk.data.model.SalatLearningModel
import com.ibadat.sdk.data.model.SalatModelItem
import com.ibadat.sdk.fragments.CallBackSalatLearningDetails

import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.views.MyCustomTextView

internal class NamazShikkaDetailsNewRecyclerViewAdapter(
    var salatLearningModel: List<SalatLearningModel>?,
    var context: Context?,
    var callBackSalatLearningDetails: CallBackSalatLearningDetails
) :
    RecyclerView.Adapter<NamazShikkaDetailsNewRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.namaz_shikka_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topicName: String? = salatLearningModel!![position].getTopicName()
        holder.ctvNamazShikhshaItem.text = topicName
        holder.namazSikkhaCount.text = LanguageConverter.getDateInBangla((position + 1).toString())
        holder.bind(salatLearningModel!![position])
        holder.itemView.setOnClickListener {
            callBackSalatLearningDetails.onItemclick2(
                position,
                salatLearningModel!![position].getId()
            )
        }
        holder.namazImage.setImageURI(Util.getUriFromPath(context!!, "drawable-hdpi/art.png"))
    }

    override fun getItemCount(): Int {
        return salatLearningModel!!.size
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        val ctvNamazShikhshaItem: MyCustomTextView
        val ivNamazShikkaNext: ImageView
        var namazSikkhaCount: MyCustomTextView
        var namazImage: ImageView
        fun bind(item: SalatModelItem) {
        }

        fun bind(item: SalatLearningModel) {
        }

        init {
            ctvNamazShikhshaItem = mView.findViewById(R.id.txtNamaz_shikhsha_item)
            ivNamazShikkaNext = mView.findViewById(R.id.ivNamaz_Shikka_next)
            namazSikkhaCount = mView.findViewById(R.id.namazShikkaCount)
            namazImage = mView.findViewById(R.id.namazImage)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: SalatLearningModel?)
    }
}