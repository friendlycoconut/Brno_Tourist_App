package com.pv239.brnotouristapp.district

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pv239.brnotouristapp.R


class DistrictViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val districtTextView: TextView = view.findViewById(R.id.districtTextView)
    private val districtImageView: ImageView = view.findViewById(R.id.districtImageView)

    fun bind(district: District) {
        districtTextView.text = district.name
        districtImageView.load(district.imageUrl)
    }
}

class DistrictListAdapter(
    private val clickHandler: (District) -> Unit // Unit = void
) : ListAdapter<District, DistrictViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<District>() {
            override fun areItemsTheSame(oldItem: District, newItem: District): Boolean {
                return oldItem === newItem // === - do oldItem and newItem have the exact object reference?
            }

            override fun areContentsTheSame(
                oldItem: District,
                newItem: District
            ): Boolean {
                return oldItem == newItem // == - are their contents same? (don't need to be the same object)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.district_item, parent, false)
        return DistrictViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }
}