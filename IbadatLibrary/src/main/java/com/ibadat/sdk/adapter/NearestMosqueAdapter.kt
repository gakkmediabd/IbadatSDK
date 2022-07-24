package com.ibadat.sdk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.nearby.PlaceInfo
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.util.handleClickEvent


internal class NearestMosqueAdapter(
    placeInfoList: MutableList<PlaceInfo>
) :
    RecyclerView.Adapter<NearestMosqueAdapter.MosqueViewHolder>() {
    var placeInfoList: MutableList<PlaceInfo>

    init {
        this.placeInfoList = placeInfoList
    }

    fun updatePlaceInfo(list: MutableList<PlaceInfo>) {
        placeInfoList = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MosqueViewHolder {
        return MosqueViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_nearest_mosque, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MosqueViewHolder, position: Int) {
        val placeInfo = placeInfoList[position]
        holder.itemView.setOnClickListener {
            holder.bind(placeInfo, onItemClick)
        }
//        holder.bindingMosqueHList?.let { binding ->
//            placeInfoList?.let {
//                val placeInfo = it[position]
//                placeInfo.let { pi ->
//                    binding.placeinfo = pi
//                    holder.bind(pi, onItemClick)
//                }
//            }
//        }
    }

    override fun getItemCount(): Int {
        return placeInfoList.size
    }

    private var onItemClick: MapItemClickListener = null

    fun setOnItemClickListener(listener: MapItemClickListener) {
        onItemClick = listener
    }

    inner class MosqueViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val acivMosque: AppCompatImageView = itemView.findViewById(R.id.aciv_mosque)
        var tvTitleMosque: TextView = itemView.findViewById(R.id.tv_title_mosque)
        var tvLocationMosque: TextView = itemView.findViewById(R.id.tv_location_mosque)
        val acivDirection: AppCompatImageView = itemView.findViewById(R.id.aciv_direction)

        fun bind(placeInfo: PlaceInfo, onItemClick: MapItemClickListener) {
            acivMosque.setImageURI(
                Util.getUriFromPath(
                    itemView.context, AppConstantUtils.drawable_hdpi + "mosque_gray.png"
                )
            )
            itemView.handleClickEvent {
                onItemClick.let { click ->
                    click?.invoke(placeInfo)
                }
            }
        }
    }
}
typealias MapItemClickListener = ((PlaceInfo?) -> Unit)?
