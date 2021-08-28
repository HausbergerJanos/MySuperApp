package com.hausberger.mysuperapp.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "town")
    val town: String
)