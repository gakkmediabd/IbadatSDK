package com.ibadat.sdk.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.SalatLearningModel
import com.ibadat.sdk.data.model.SalatModelItem
import com.ibadat.sdk.fragments.CallBackSalatLearning
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.views.MyCustomTextView

internal class NamazShikkaNewRecyclerViewAdapter(
    var salatLearningModel: List<SalatLearningModel>?,
    activity: FragmentActivity?,
   var callBackSalatLearning: CallBackSalatLearning
) :
    RecyclerView.Adapter<NamazShikkaNewRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.namaz_shikka_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topicName: String? = salatLearningModel!![position].getTopicName()
        holder.namazSikkhaCount.text = LanguageConverter.getDateInBangla((position + 1).toString())
        holder.Namaz_shikhsha_item.text = topicName
        holder.bind(salatLearningModel!![position])
        holder.itemView.setOnClickListener {
            callBackSalatLearning.onItemclick(position, salatLearningModel!![position].getId())
        }
        holder.namazImage.setImageURI(Util.getUriFromPath(holder.itemView.context, AppConstantUtils.drawable_hdpi + "art.png"))
    }


    override fun getItemCount(): Int {
      return salatLearningModel!!.size
    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        val Namaz_shikhsha_item: MyCustomTextView
        val ivNamaz_Shikka_next: ImageView
        var namazSikkhaCount: MyCustomTextView
        var namazImage: ImageView
        fun bind(item: SalatModelItem) {
        }

        fun bind(item: SalatLearningModel) {

        }

        init {
            Namaz_shikhsha_item = mView.findViewById(R.id.txtNamaz_shikhsha_item)
            ivNamaz_Shikka_next = mView.findViewById(R.id.ivNamaz_Shikka_next)
            namazSikkhaCount = mView.findViewById(R.id.namazShikkaCount)
            namazImage = mView.findViewById(R.id.namazImage)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: SalatLearningModel?)
    }



}