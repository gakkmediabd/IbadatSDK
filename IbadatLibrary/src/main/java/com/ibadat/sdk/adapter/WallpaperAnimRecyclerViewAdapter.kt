package com.ibadat.sdk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.WallpaperAnimModel
import com.ibadat.sdk.util.ClickEventCounter
import com.ibadat.sdk.util.CustomDialog

internal class WallpaperAnimRecyclerViewAdapter(
    private val mValues: List<WallpaperAnimModel?>?,
    var context: Context,
    var requestType: String
) :
    RecyclerView.Adapter<WallpaperAnimRecyclerViewAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.wallpaper_anim_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val previewUrl: String = mValues!![position]?.previwUrl!!
        Glide.with(context)
            .load(previewUrl.replace(" ".toRegex(), "%20"))
            .apply(RequestOptions().placeholder(R.drawable.default_img))
            .into(holder.imgPreview)
        holder.mView.setOnClickListener {
            ClickEventCounter.setCountClickEvent()
            CustomDialog.showDownloadConfirmationDialog(
                context, mValues[position]?.title, mValues[position]?.physicalUrl,
                requestType
            )
        }
    }

    override fun getItemCount(): Int {
        return mValues?.size ?: 0
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        val imgPreview: ImageView = mView.findViewById(R.id.imgPreview)
        var progress: ProgressBar = mView.findViewById(R.id.progress)
    }
}
