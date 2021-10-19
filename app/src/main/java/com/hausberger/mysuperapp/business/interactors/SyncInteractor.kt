package com.hausberger.mysuperapp.business.interactors

import android.content.Context
import android.net.ConnectivityManager
import androidx.work.*
import com.hausberger.mysuperapp.framework.presentation.contentprovider.SyncPlaceWorker
import com.hausberger.mysuperapp.framework.util.checkForInternet
import java.util.concurrent.TimeUnit

open class SyncInteractor
constructor(
    private val appContext: Context
) {

    protected fun enqueueSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest =
            OneTimeWorkRequestBuilder<SyncPlaceWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MICROSECONDS
                )
                .build()

        WorkManager
            .getInstance(appContext)
            .enqueueUniqueWork(
                "syncPlaces",
                ExistingWorkPolicy.REPLACE,
                syncWorkRequest
            )
    }

    protected fun checkForInternet(): Boolean = appContext.checkForInternet()
}