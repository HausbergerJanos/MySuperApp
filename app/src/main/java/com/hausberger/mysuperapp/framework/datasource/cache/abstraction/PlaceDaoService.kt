package com.hausberger.mysuperapp.framework.datasource.cache.abstraction

import com.hausberger.mysuperapp.business.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceDaoService {

    suspend fun insertPlace(place: Place): Long

    suspend fun getPlaces(): Flow<List<Place>>

    suspend fun updatePlace(id: Int, externalId: String, synced: Boolean): Int
}