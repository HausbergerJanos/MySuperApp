package com.hausberger.mysuperapp.business.data.network.abstraction

import com.hausberger.mysuperapp.business.domain.model.Place

interface PlaceNetworkDataSource {

    fun createPlace(
        place: Place,
        successCallback: (externalId: String) -> Unit,
        errorCallback: () -> Unit
    )
}