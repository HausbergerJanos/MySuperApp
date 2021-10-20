package com.hausberger.mysuperapp.business.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    var id: String = "",
    var externalId: String = "",
    val town: String,
    val country: String,
    var synced: Boolean = false
): java.io.Serializable