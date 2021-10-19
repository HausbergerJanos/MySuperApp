package com.hausberger.mysuperapp.framework.datasource.cache.database

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: PlaceEntity): Long

    @Query("SELECT * FROM places")
    fun getPlaces(): Flow<List<PlaceEntity>>

    @Query("""
        SELECT * FROM places 
        WHERE _id = :id
    """)
    suspend fun getPlaceById(id: Int): PlaceEntity

    /**
     * Select all places.
     *
     * @return A [Cursor] of all the places in the table.
     */
    @Query("SELECT * FROM places ORDER BY :sortOrder")
    fun selectAll(sortOrder: String): Cursor?

    @RawQuery
    fun rawOperation(query: SupportSQLiteQuery?): Cursor?

    /**
     * Select a place by the ID.
     *
     * @param id The row ID.
     * @return A [Cursor] of the selected place.
     */
    @Query("SELECT * FROM places WHERE _id = :id")
    fun selectById(id: String): Cursor?

    /**
     * Delete a place by the ID.
     *
     * @param id The row ID.
     * @return A number of places deleted. This should always be `1`.
     */
    @Query("DELETE FROM places WHERE _id = :id")
    fun deleteById(id: Int): Int

    /**
     * Counts the number of places in the table.
     *
     * @return The number of places.
     */
    @Query("SELECT COUNT(*) FROM places")
    fun count(): Int

    /**
     * Update the place. The place is identified by the row ID.
     *
     * @param place The place to update.
     * @return A number of places updated. This should always be `1`.
     */
    @Update
    fun update(place: PlaceEntity?): Int

    @Query("""
        UPDATE places
        SET 
        external_id = :externalId,
        synced = :synced
        WHERE _id = :id
    """)
    fun updatePlace(id: Int, externalId: String, synced: Boolean): Int
}