package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hausberger.mysuperapp.databinding.ViewItemPlaceBinding
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity

class PlaceAdapter : ListAdapter<PlaceEntity, PlaceAdapter.PlaceItemViewHolder>(PlaceComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceItemViewHolder {
        val binding = ViewItemPlaceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class PlaceItemViewHolder(
        private val binding: ViewItemPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(place: PlaceEntity) {
            binding.apply {
                town.text = place.town
                country.text = place.country
            }
        }
    }
}