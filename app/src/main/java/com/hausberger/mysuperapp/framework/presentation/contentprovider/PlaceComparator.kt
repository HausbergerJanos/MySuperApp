package com.hausberger.mysuperapp.framework.presentation.contentprovider

import androidx.recyclerview.widget.DiffUtil
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity

class PlaceComparator : DiffUtil.ItemCallback<PlaceEntity>() {

    override fun areItemsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
        return oldItem.town == newItem.town
    }

    override fun areContentsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
        return oldItem == newItem
    }
}