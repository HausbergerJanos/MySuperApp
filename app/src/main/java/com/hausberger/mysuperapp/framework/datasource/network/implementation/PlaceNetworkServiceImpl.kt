package com.hausberger.mysuperapp.framework.datasource.network.implementation

import com.google.firebase.database.DatabaseReference
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.network.abstraction.PlaceNetworkService
import java.util.*
import javax.inject.Inject

class PlaceNetworkServiceImpl
@Inject
constructor(
    private val placeRef: DatabaseReference
) : PlaceNetworkService {

    override fun createPlace(
        place: Place,
        successCallback: (externalId: String) -> Unit,
        errorCallback: () -> Unit
    ) {

        val externalId = UUID.randomUUID().toString()

        placeRef.child("places").child(externalId).setValue(
            place
        ) { error, _ ->
            if (error == null) {
                successCallback.invoke(externalId)
            } else {
                errorCallback.invoke()
            }
        }
    }
}