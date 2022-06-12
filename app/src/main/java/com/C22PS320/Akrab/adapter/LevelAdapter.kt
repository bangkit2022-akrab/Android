package com.C22PS320.Akrab.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.network.response.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class LevelAdapter(private val listUser: List<DataItem?>?) : RecyclerView.Adapter<LevelAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_level, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setBackgroundColor(Color.LTGRAY)
        Glide.with(viewHolder.itemView.context)
            .load("${listUser?.get(position)?.imageLevel}")
            .transform(CenterInside(), RoundedCorners(35))
            .into(viewHolder.imgItemPhoto)
        viewHolder.itemView.setOnClickListener { listUser?.get(viewHolder.bindingAdapterPosition)
            ?.let { it1 ->
            onItemClickCallback.onItemClicked(
                it1.level.toString()
            )
        } }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }
    override fun getItemCount() = listUser?.size ?: 0
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgItemPhoto: ImageView = view.findViewById(R.id.imgItemPhoto)
    }
}