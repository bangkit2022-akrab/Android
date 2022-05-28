package com.C22PS320.Akrab.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.network.response.DataItem
import com.bumptech.glide.Glide

class LevelAdapter(private val listUser: List<DataItem?>?) : RecyclerView.Adapter<LevelAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_level, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setBackgroundColor(Color.LTGRAY)
        viewHolder.tvItemName.text = listUser?.get(position)?.level
        Glide.with(viewHolder.itemView.context)
            .load("${listUser?.get(position)?.imageLevel}")
            .into(viewHolder.imgItemPhoto)
        viewHolder.itemView.setOnClickListener { listUser?.get(viewHolder.adapterPosition)
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
        val tvItemName: TextView = view.findViewById(R.id.tvItemName)
        val imgItemPhoto: ImageView = view.findViewById(R.id.imgItemPhoto)
    }
}