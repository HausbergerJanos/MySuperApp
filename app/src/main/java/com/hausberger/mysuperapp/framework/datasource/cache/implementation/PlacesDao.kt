package com.hausberger.mysuperapp.framework.datasource.cache.implementation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity

@Dao
interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity): Long

    @Query("SELECT * FROM places")
    suspend fun getPlaces(): List<PlaceEntity>
}