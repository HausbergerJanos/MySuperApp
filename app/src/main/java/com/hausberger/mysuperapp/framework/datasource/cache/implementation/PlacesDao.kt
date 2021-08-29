package com.hausberger.mysuperapp.framework.datasource.cache.implementation

import android.database.Cursor
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

    /**
     * Select all places.
     *
     * @return A [Cursor] of all the places in the table.
     */
    @Query("SELECT * FROM places")
    fun selectAll(): Cursor?

    /**
     * Select a place by the ID.
     *
     * @param id The row ID.
     * @return A [Cursor] of the selected place.
     */
    @Query("SELECT * FROM places WHERE _id = :id")
    fun selectById(id: String): Cursor?
}