package com.hausberger.mysuperapp.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun placesDao(): PlacesDao
}