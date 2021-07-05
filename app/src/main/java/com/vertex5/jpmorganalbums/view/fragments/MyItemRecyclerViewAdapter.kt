package com.vertex5.jpmorganalbums.view.fragments

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vertex5.jpmorganalbums.R
import com.vertex5.jpmorganalbums.databinding.ItemAlbumBinding
import com.vertex5.jpmorganalbums.model.data.Album


import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val values = LinkedList<Album>()
    private lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setAlbumList(list: List<Album>){
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.item = item
        holder.setContent()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        var item: Album?=null

        fun setContent(){
            binding.txtAlbumName.text = item?.title
        }

        override fun toString(): String {
            return super.toString() + " '" + item?.title + "'"
        }
    }

}