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

class DeletePlaceInteractor
@Inject
constructor(
    private val placeCacheDataSource: PlaceCacheDataSource,
    private val placeNetworkDataSource: PlaceNetworkDataSource,
    private val unsyncedTransactionsDaoService: UnsyncedTransactionsDaoService,
    @ApplicationContext appContext: Context
) : SyncInteractor(appContext) {

    suspend fun deletePlace(place: Place) {
        var pendingTransaction = unsyncedTransactionsDaoService
            .getPendingTransactionByEntityId(place.id.toInt())

        pendingTransaction?.let { transaction ->
            if (transaction.transactionType == 0) {
                placeCacheDataSource.deletePlace(place.id.toInt())
                unsyncedTransactionsDaoService.deleteTransaction(transaction.id)
                return
            }
        }

        if (pendingTransaction == null || pendingTransaction.transactionType != 2) {
            pendingTransaction = UnsyncedTransactionEntity(
                entityId = place.id.toInt(),
                entityTableName = PlaceContract.TABLE_NAME,
                transactionType = 2
            )

            unsyncedTransactionsDaoService.insert(pendingTransaction)

            placeCacheDataSource.updatePlace(place.id.toInt(), false)
        }

        if (checkForInternet()) {
            try {
                placeNetworkDataSource.deletePlace(place)
                placeCacheDataSource.deletePlace(place.id.toInt())
                unsyncedTransactionsDaoService.deleteTransaction(pendingTransaction.id)
            } catch (e: Exception) {
                enqueueSyncWorker()
            }
        } else {
            enqueueSyncWorker()
        }

        Log.d("TAG", "deletePlace: ")
    }
}