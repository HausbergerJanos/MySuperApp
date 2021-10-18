package com.hausberger.mysuperapp.business.domain.model

data class Place(
    var id: String = "",
    val town: String,
    val country: String,
    var synced: Boolean = false
)