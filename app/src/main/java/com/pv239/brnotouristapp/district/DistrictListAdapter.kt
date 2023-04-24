package com.pv239.brnotouristapp.district

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ComponentRegistry
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.pv239.brnotouristapp.R

class DistrictViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val districtTextView: TextView = view.findViewById(R.id.districtTextView)
    private val districtImageView: ImageView = view.findViewById(R.id.districtImageView)

    fun bind(district: District) {
        districtTextView.text = district.name
//        districtImageView.load(district.imageUrl)
        districtImageView.load("https://images.squarespace-cdn.com/content/v1/57769f30414fb56f6c8552ab/1627410192463-DBKIHR4XQSDO7JKL7UI5/med.jpg")
    }

    private fun ImageView.loadImageFromUrl(imageUrl: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .components(fun ComponentRegistry.Builder.() {
                add(SvgDecoder.Factory())
            })
            .build()

        val imageRequest = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(300)
            .data(imageUrl)
            .target(
                onStart = {
                    //set up an image loader or whatever you need
                },
                onSuccess = { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    this.setImageBitmap(bitmap)
                    //dismiss the loader if any
                },
                onError = {
                    // error
                }
            )
            .build()

        imageLoader.enqueue(imageRequest)
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