package com.hausberger.mysuperapp.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hausberger.mysuperapp.framework.datasource.provider.PlaceContract

@Entity(tableName = PlaceContract.TABLE_NAME)
data class PlaceEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PlaceContract.COLUMN_ID, index = true)
    val id: Int = 0,

    @ColumnInfo(name = PlaceContract.COLUMN_TOWN)
    val town: String,

    @ColumnInfo(name = PlaceContract.COLUMN_COUNTRY)
    val country: String
)