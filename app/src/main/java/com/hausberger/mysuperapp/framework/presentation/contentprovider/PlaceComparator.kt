package com.hausberger.mysuperapp.framework.presentation.contentprovider

import androidx.recyclerview.widget.DiffUtil
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity

class PlaceComparator : DiffUtil.ItemCallback<Place>() {

    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
}