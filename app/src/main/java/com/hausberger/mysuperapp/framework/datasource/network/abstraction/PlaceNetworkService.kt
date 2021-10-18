package com.hausberger.mysuperapp.framework.datasource.network.abstraction

import com.hausberger.mysuperapp.business.domain.model.Place

interface PlaceNetworkService {

    fun createPlace(
        place: Place,
        successCallback: (externalId: String) -> Unit,
        errorCallback: () -> Unit
    )
}