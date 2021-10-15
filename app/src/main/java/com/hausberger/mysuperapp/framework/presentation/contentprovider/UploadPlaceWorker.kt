package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import java.lang.Exception
import java.util.*

class UploadPlaceWorker
constructor(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        Log.d("MY_WORKER-->", "Upload Place Started")
        val firebaseDatabase: FirebaseDatabase =
            Firebase.database("https://mysuperapp-49a9b-default-rtdb.firebaseio.com")
        val placeRef: DatabaseReference = firebaseDatabase.reference
        return Result.success()
    }
}