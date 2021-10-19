package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.databinding.ViewItemPlaceBinding
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity

class PlaceAdapter
constructor(
    private val interactor: Interactor
) : ListAdapter<Place, PlaceAdapter.PlaceItemViewHolder>(PlaceComparator()) {

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

        fun bind(place: Place) {
            binding.apply {
                town.text = place.town
                country.text = place.country
                town.alpha = if (place.synced) 1f else 0.5f
                country.alpha = if (place.synced) 1f else 0.5f

                root.setOnClickListener {
                    interactor.onPlaceClick(place)
                }
            }
        }
    }

    interface Interactor {
        fun onPlaceClick(place: Place)
    }
}