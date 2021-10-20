package com.hausberger.mysuperapp.business.data.cache.abstraction

import com.hausberger.mysuperapp.business.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceCacheDataSource {

    suspend fun insertPlace(place: Place): Long

    suspend fun getPlaceById(id: String): Place

    suspend fun getPlaces(): Flow<List<Place>>

    suspend fun updatePlace(id: String, synced: Boolean): Int

    suspend fun deletePlace(id: String): Int
}