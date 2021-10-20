package com.hausberger.mysuperapp.business.interactors

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.work.*
import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.cache.abstraction.UnsyncedTransactionsDaoService
import com.hausberger.mysuperapp.framework.datasource.cache.model.UnsyncedTransactionEntity
import com.hausberger.mysuperapp.framework.datasource.provider.contentprovider.PlaceContract
import com.hausberger.mysuperapp.framework.presentation.contentprovider.SyncPlaceWorker
import com.hausberger.mysuperapp.framework.util.checkForInternet
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CreatePlaceInteractor
@Inject
constructor(
    private val placeCacheDataSource: PlaceCacheDataSource,
    private val placeNetworkDataSource: PlaceNetworkDataSource,
    private val unsyncedTransactionsDaoService: UnsyncedTransactionsDaoService,
    @ApplicationContext appContext: Context
) : SyncInteractor(appContext) {

    suspend fun cratePlace(place: Place) {
        // Create place and save it in to the local DB
        val innerId = placeCacheDataSource.insertPlace(place)
        place.id = innerId.toString()

        // Create and insert unsynced transaction
        val transaction = UnsyncedTransactionEntity(
            entityId = innerId.toInt(),
            entityTableName = PlaceContract.TABLE_NAME,
            transactionType = 0
        )
        val pendingTransactionId = unsyncedTransactionsDaoService.insert(transaction)

        // Check for Internet connection
        if (checkForInternet()) {
            // There is Internet connection
            withContext(IO) {
                try {
                    // Create place in network DB
                    val externalId = placeNetworkDataSource.createPlace(place)

                    // Update local entity with externalId and set it to synced
                    placeCacheDataSource.updatePlace(innerId.toInt(), externalId, true)

                    // Delete pending transaction
                    unsyncedTransactionsDaoService.deleteTransaction(pendingTransactionId.toInt())
                } catch (e: Exception) {
                    // There is an API/Generic error. Enqueue sync worker
                    enqueueSyncWorker()
                }
            }
        } else {
            // There is no Internet connection. Enqueue sync worker
            enqueueSyncWorker()
        }
    }
}