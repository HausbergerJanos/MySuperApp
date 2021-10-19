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

    override suspend fun createPlace(place: Place): String {
        return suspendCoroutine { cont ->
            createPlaceOnFirestore(
                place = place,
                successCallback = { externalId ->
                    cont.resumeWith(Result.success(externalId))
                },
                errorCallback = { exception ->
                    cont.resumeWith(Result.failure(exception))
                }
            )
        }
    }

    private fun createPlaceOnFirestore(
        place: Place,
        successCallback: (externalId: String) -> Unit,
        errorCallback: (exception: Exception) -> Unit
    ) {
        val externalId = UUID.randomUUID().toString()

        firebaseFirestore
            .collection("places")
            .document(externalId)
            .set(place)
            .addOnSuccessListener {
                successCallback.invoke(externalId)
            }
            .addOnFailureListener { exception ->
                errorCallback.invoke(exception)
            }
    }
}