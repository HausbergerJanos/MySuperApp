package com.hausberger.mysuperapp.business.interactors

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.business.domain.model.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreatePlaceInteractor
@Inject
constructor(
    private val placeCacheDataSource: PlaceCacheDataSource,
    private val placeNetworkDataSource: PlaceNetworkDataSource
) {

    suspend fun cratePlace(
        scope: CoroutineScope,
        place: Place
    ): Boolean {
        val innerId = placeCacheDataSource.insertPlace(place)
        place.id = innerId.toString()

        placeNetworkDataSource.createPlace(
            place = place,
            successCallback = { externalId ->
                scope.launch {
                    placeCacheDataSource.updatePlace(innerId.toInt(), externalId, true)
                }
            },
            errorCallback = {

            }
        )

        return innerId > 0
    }
}