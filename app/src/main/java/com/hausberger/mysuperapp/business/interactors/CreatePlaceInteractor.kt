package com.hausberger.mysuperapp.business.interactors

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.util.checkForInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CreatePlaceInteractor
@Inject
constructor(
    private val placeCacheDataSource: PlaceCacheDataSource,
    private val placeNetworkDataSource: PlaceNetworkDataSource
) {

    suspend fun cratePlace(
        connectivityManager: ConnectivityManager,
        place: Place
    ): Boolean {
        // Create place and save it in to the local DB
        val innerId = placeCacheDataSource.insertPlace(place)
        place.id = innerId.toString()

        // Check for Internet connection
        if (connectivityManager.checkForInternet()) {
            // There is Internet connection
            withContext(IO) {
                try {
                    //
                    val externalId = placeNetworkDataSource.createPlace(place)
                    placeCacheDataSource.updatePlace(innerId.toInt(), externalId, true)
                    Log.d("TAG", "cratePlace success")
                } catch (e: Exception) {
                    Log.d("TAG", "cratePlace failed")
                }
            }
        } else {
            // There is no Internet connection
            Log.d("TAG", "cratePlace: ")
        }

        return innerId > 0
    }
}