package com.ibadat.sdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.data.model.nearby.PlaceInfo
import com.ibadat.sdk.databinding.RowItemNearestMosqueBinding
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.util.handleClickEvent


internal class NearestMosqueAdapter(
    placeInfoList: MutableList<PlaceInfo>
) :
    RecyclerView.Adapter<NearestMosqueAdapter.MosqueViewHolder>() {
    var placeInfoList: MutableList<PlaceInfo>?
    init {
        this.placeInfoList = placeInfoList
    }

    fun updatePlaceInfo(list: MutableList<PlaceInfo>) {
        placeInfoList = list
    }

    inner class MosqueViewHolder(itemView: RowItemNearestMosqueBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        var bindingMosqueHList: RowItemNearestMosqueBinding? = itemView

        fun bind(placeInfo: PlaceInfo, onItemClick: MapItemClickListener) {
            bindingMosqueHList?.imgMosque?.setImageURI(
                Util.getUriFromPath(
                    itemView.context,
                    "drawable-hdpi/mosque_gray.png"
                )
            )
            bindingMosqueHList?.root?.handleClickEvent {
                onItemClick.let { click ->
                    click?.invoke(placeInfo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MosqueViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_item_nearest_mosque,
            parent,
            false
        )
        return MosqueViewHolder(binding as RowItemNearestMosqueBinding)
    }

    override fun onBindViewHolder(holder: MosqueViewHolder, position: Int) {

        holder.bindingMosqueHList?.let { binding ->
            placeInfoList?.let {
                val placeInfo = it[position]
                placeInfo.let { pi ->
                    binding.placeinfo = pi
                    holder.bind(pi, onItemClick)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return placeInfoList!!.size
    }

    private var onItemClick: MapItemClickListener = null

    fun setOnItemClickListener(listener: MapItemClickListener) {
        onItemClick = listener
    }

}
typealias MapItemClickListener = ((PlaceInfo?) -> Unit)?
