package com.hausberger.mysuperapp.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import com.hausberger.mysuperapp.framework.datasource.cache.model.UnsyncedTransactionEntity

@Database(
    entities = [
        UnsyncedTransactionEntity::class,
        PlaceEntity::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun placesDao(): PlacesDao

    abstract fun unsyncedTransactionsDao(): UnsyncedTransactionsDao
}