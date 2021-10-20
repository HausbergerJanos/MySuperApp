package com.hausberger.mysuperapp.framework.datasource.network.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.network.abstraction.PlaceNetworkService
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class PlaceNetworkServiceImpl
@Inject
constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PlaceNetworkService {

    override suspend fun insertOrUpdatePlace(place: Place) {
        return suspendCoroutine { cont ->
            createPlaceOnFirestore(
                place = place,
                successCallback = {
                    cont.resumeWith(Result.success(Unit))
                },
                errorCallback = { exception ->
                    cont.resumeWith(Result.failure(exception))
                }
            )
        }
    }

    override suspend fun deletePlace(place: Place) {
        return suspendCoroutine { cont ->
            deletePlaceOnFirestore(
                place = place,
                successCallback = {
                    cont.resumeWith(Result.success(Unit))
                },
                errorCallback = { exception ->
                    cont.resumeWith(Result.failure(exception))
                }
            )
        }
    }

    private fun createPlaceOnFirestore(
        place: Place,
        successCallback: () -> Unit,
        errorCallback: (exception: Exception) -> Unit
    ) {
        firebaseFirestore
            .collection("places")
            .document(place.id)
            .set(place)
            .addOnSuccessListener {
                successCallback.invoke()
            }
            .addOnFailureListener { exception ->
                errorCallback.invoke(exception)
            }
    }

    private fun deletePlaceOnFirestore(
        place: Place,
        successCallback: () -> Unit,
        errorCallback: (exception: Exception) -> Unit
    ) {
        firebaseFirestore
            .collection("places")
            .document(place.id)
            .delete()
            .addOnSuccessListener {
                successCallback.invoke()
            }
            .addOnFailureListener { exception ->
                errorCallback.invoke(exception)
            }
    }
}