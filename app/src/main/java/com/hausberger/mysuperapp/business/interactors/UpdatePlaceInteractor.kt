package com.hausberger.mysuperapp.business.interactors

import android.content.Context
import android.util.Log
import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.cache.abstraction.UnsyncedTransactionsDaoService
import com.hausberger.mysuperapp.framework.datasource.cache.model.UnsyncedTransactionEntity
import com.hausberger.mysuperapp.framework.datasource.provider.contentprovider.PlaceContract
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class UpdatePlaceInteractor
@Inject
constructor(
    private val placeCacheDataSource: PlaceCacheDataSource,
    private val placeNetworkDataSource: PlaceNetworkDataSource,
    private val unsyncedTransactionsDaoService: UnsyncedTransactionsDaoService,
    @ApplicationContext appContext: Context
) : SyncInteractor(appContext) {

    suspend fun updatePlace(place: Place) {
        var pendingTransaction = unsyncedTransactionsDaoService
            .getPendingTransactionByEntityId(place.id)

        pendingTransaction?.let { transaction ->
            if (transaction.transactionType == 2) {
                unsyncedTransactionsDaoService.deleteTransaction(transaction.id)
            }
        }

        if (pendingTransaction == null || pendingTransaction.transactionType == 2) {
            pendingTransaction = UnsyncedTransactionEntity(
                entityId = place.id,
                entityTableName = PlaceContract.TABLE_NAME,
                transactionType = 1
            )

            unsyncedTransactionsDaoService.insert(pendingTransaction)
        }

        placeCacheDataSource.insertPlace(place)
        placeCacheDataSource.updatePlace(place.id, false)

        if (checkForInternet()) {
            try {
                // Update place in remote DB
                placeNetworkDataSource.insertOrUpdatePlace(place)

                // Update local entity with externalId and set it to synced
                placeCacheDataSource.updatePlace(place.id, true)

                // Delete pending transaction
                unsyncedTransactionsDaoService.deleteTransaction(pendingTransaction.id)
            } catch (e: Exception) {
                enqueueSyncWorker()
            }
        } else {
            enqueueSyncWorker()
        }

        Log.d("TAG", "updatePlace: ")
    }
}