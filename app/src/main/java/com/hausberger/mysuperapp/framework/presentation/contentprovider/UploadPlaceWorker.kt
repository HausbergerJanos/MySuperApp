package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import kotlinx.coroutines.delay

class UploadPlaceWorker
constructor(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        Log.d("MY_WORKER-->", "Upload Place Started")
        var firebaseDatabase: FirebaseDatabase = Firebase.database("https://mysuperapp-49a9b-default-rtdb.firebaseio.com")
        var placeRef: DatabaseReference = firebaseDatabase.getReference("places")

        placeRef.setValue(PlaceEntity(
            town = "parasznya",
            country = "hungary"
        ))
        Log.d("MY_WORKER-->", "Upload Place Finished")
        return Result.success()
    }
}