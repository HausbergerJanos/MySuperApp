package com.hausberger.mysuperapp.framework.datasource.cache.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hausberger.mysuperapp.framework.datasource.provider.PlaceContract


@Entity(tableName = PlaceContract.TABLE_NAME)
data class PlaceEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PlaceContract.COLUMN_ID, index = true)
    var id: Int = 0,

    @ColumnInfo(name = PlaceContract.COLUMN_TOWN)
    val town: String,

    @ColumnInfo(name = PlaceContract.COLUMN_COUNTRY)
    val country: String
) {

    companion object {
        /**
         * Create a new [PlaceEntity] from the specified [ContentValues].
         *
         * @param values A [ContentValues] that at least contain [.COLUMN_NAME].
         * @return A newly created [PlaceEntity] instance.
         */
        fun fromContentValues(values: ContentValues?): PlaceEntity {
            var town = ""
            var country = ""

            values?.let { safeValues ->
                safeValues.apply {
                    if (containsKey(PlaceContract.COLUMN_TOWN)) {
                        town = getAsString(PlaceContract.COLUMN_TOWN)
                    }

                    if (containsKey(PlaceContract.COLUMN_COUNTRY)) {
                        country = getAsString(PlaceContract.COLUMN_COUNTRY)
                    }
                }
            }

            return PlaceEntity(
                town = town,
                country = country
            )
        }
    }
}