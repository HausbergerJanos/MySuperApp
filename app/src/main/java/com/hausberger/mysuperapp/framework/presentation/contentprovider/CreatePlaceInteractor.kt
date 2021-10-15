package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CreatePlaceInteractor
@Inject
constructor(
    private val placesLocalDatabase: PlacesDao
) {

    val firebaseDatabase: FirebaseDatabase =
        Firebase.database("https://mysuperapp-49a9b-default-rtdb.firebaseio.com")
    val placeRef: DatabaseReference = firebaseDatabase.reference

    suspend fun cratePlace(place: PlaceEntity): Boolean {
        val result = placesLocalDatabase.insert(place)

        placeRef.child("places").child(UUID.randomUUID().toString()).setValue(
            place
        ) { error, ref ->
            if (error == null) {
                Log.d("FIREBASE-->", "Success")
            } else {
                Log.d("FIREBASE-->", "Failed")
            }
        }

        return result > 0
    }
}