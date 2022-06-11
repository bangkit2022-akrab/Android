package com.C22PS320.Akrab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.network.response.DataItemModule
import com.bumptech.glide.Glide

class ModuleAdapter(private val listUser: List<DataItemModule?>?) : RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_module, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context)
            .load("${listUser?.get(position)?.image}")
            .into(viewHolder.imgItemPhoto)
        viewHolder.txtItem.text = listUser?.get(position)?.materi
        viewHolder.itemView.setOnClickListener { listUser?.get(viewHolder.bindingAdapterPosition)
            ?.let { it1 ->
                onItemClickCallback.onItemClicked(
                    it1
                )
            } }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DataItemModule)
    }
    override fun getItemCount() = listUser?.size ?: 0
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgItemPhoto: ImageView = view.findViewById(R.id.icon_image_view)
        val txtItem: TextView = view.findViewById(R.id.title_text_view)
    }
}