package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.framework.datasource.cache.abstraction.UnsyncedTransactionsDaoService
import com.hausberger.mysuperapp.framework.datasource.cache.model.UnsyncedTransactionEntity
import com.hausberger.mysuperapp.framework.datasource.provider.contentprovider.PlaceContract
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception

@HiltWorker
class SyncPlaceWorker
@AssistedInject
constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val unsyncedTransactionsDaoService: UnsyncedTransactionsDaoService,
    private val placesCacheDataSource: PlaceCacheDataSource,
    private val placeNetworkDataSource: PlaceNetworkDataSource
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        Log.d("MY_WORKER-->", "Place sync has been started")

        // Get all "place" releated pending transactions
        val pendingTransactions = unsyncedTransactionsDaoService
            .getPendingTransactions(PlaceContract.TABLE_NAME)

        // Group by transaction type
        // 0 - Create
        // 2 - Delete
        val byTransactionType = pendingTransactions
                .groupBy { it.transactionType }

        val pendingCreateTransactions = byTransactionType[0]
        val processedCreateTransactions = executePendingCreateTransactions(pendingCreateTransactions)
        val createSuccess = processedCreateTransactions.containsAll(pendingCreateTransactions ?: emptyList())

        val pendingDeleteTransactions = byTransactionType[2]
        val processedDeleteTransactions = executePendingDeleteTransactions(pendingDeleteTransactions)
        val deleteSuccess = processedDeleteTransactions.containsAll(pendingDeleteTransactions ?: emptyList())

        Log.d("MY_WORKER-->", "Upload Place Finished. Success: $createSuccess + $deleteSuccess")

        return if (createSuccess && deleteSuccess) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    private suspend fun executePendingCreateTransactions(
        transactions: List<UnsyncedTransactionEntity>?
    ) : List<UnsyncedTransactionEntity> {
        val processedEntities = mutableListOf<UnsyncedTransactionEntity>()

        transactions?.let {
            for (transaction in transactions) {
                try {
                    // Get place from local DB
                    val place = placesCacheDataSource.getPlaceById(transaction.entityId)

                    // Create place in remote DB
                    val externalId = placeNetworkDataSource.insertOrUpdatePlace(place)

                    // Update place in locale DB
                    placesCacheDataSource.updatePlace(place.id, true)

                    // Remove pending transaction
                    unsyncedTransactionsDaoService.deleteTransaction(transaction.id)

                    // Add to cleared list
                    processedEntities.add(transaction)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return processedEntities
    }

    private suspend fun executePendingDeleteTransactions(
        transactions: List<UnsyncedTransactionEntity>?
    ) : List<UnsyncedTransactionEntity> {
        val processedEntities = mutableListOf<UnsyncedTransactionEntity>()

        transactions?.let {
            for (transaction in transactions) {
                try {
                    // Get place from local DB
                    val place = placesCacheDataSource.getPlaceById(transaction.entityId)

                    // Create place in remote DB
                    val externalId = placeNetworkDataSource.deletePlace(place)

                    // Update place in locale DB
                    placesCacheDataSource.deletePlace(place.id)

                    // Remove pending transaction
                    unsyncedTransactionsDaoService.deleteTransaction(transaction.id)

                    // Add to cleared list
                    processedEntities.add(transaction)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return processedEntities
    }
}