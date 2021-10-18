package com.hausberger.mysuperapp.framework.datasource.provider.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.sqlite.db.SimpleSQLiteQuery
import com.hausberger.mysuperapp.framework.datasource.cache.database.PlacesDao
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 *  A [ContentProvider] based on a Room database.
 */

class PlaceContentProvider : ContentProvider() {

    /** The URI matcher */
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        /**
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table "places" are shown.
         */

        /**
         * Sets the integer value for multiple rows in table Places to 1. Notice that no wildcard is used
         * in the path
         */
        addURI(PlaceContract.AUTHORITY, PlaceContract.TABLE_NAME, PlaceContract.CODE_PLACES_DIR)

        /**
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.hausberger.mysuperapp.provider/places/3" matches, but
         * "content://com.hausberger.mysuperapp.provider/places doesn't.
         */
        addURI(
            PlaceContract.AUTHORITY,
            PlaceContract.TABLE_NAME + "/*",
            PlaceContract.CODE_PLACES_ITEM
        )
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                PlaceContentProviderEntryPoint::class.java
            )

        var localSortOrder: String = sortOrder ?: ""

        val code = uriMatcher.match(uri)
        if (code == PlaceContract.CODE_PLACES_DIR || code == PlaceContract.CODE_PLACES_ITEM) {
            val cursor: Cursor? = if (code == PlaceContract.CODE_PLACES_DIR) {
                if (localSortOrder.isEmpty()) {
                    localSortOrder = "${PlaceContract.COLUMN_ID} ASC"
                }

                var localQuery = "SELECT * FROM ${PlaceContract.TABLE_NAME}"

                if (!selection.isNullOrEmpty()) {
                    localQuery += " WHERE"
                    localQuery += " $selection"
                }

                localQuery += " ORDER BY $localSortOrder"

                val query = SimpleSQLiteQuery(localQuery, selectionArgs)
                hiltEntryPoint.placeDao().rawOperation(query)
            } else {
                hiltEntryPoint.placeDao().selectById(uri.lastPathSegment ?: "")
            }

            cursor?.setNotificationUri(context?.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri");
        }
    }

    override fun getType(uri: Uri): String {
        return when (uriMatcher.match(uri)) {
            PlaceContract.CODE_PLACES_DIR -> "vnd.android.cursor.dir/" + PlaceContract.AUTHORITY + "." + PlaceContract.TABLE_NAME
            PlaceContract.CODE_PLACES_ITEM -> "vnd.android.cursor.item/" + PlaceContract.AUTHORITY + "." + PlaceContract.TABLE_NAME
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        when (uriMatcher.match(uri)) {
            PlaceContract.CODE_PLACES_DIR -> {
                val appContext = context?.applicationContext ?: throw IllegalStateException()
                val hiltEntryPoint =
                    EntryPointAccessors.fromApplication(
                        appContext,
                        PlaceContentProviderEntryPoint::class.java
                    )

                val id =
                    hiltEntryPoint.placeDao().insertPlace(PlaceEntity.fromContentValues(values))
                appContext.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }

            PlaceContract.CODE_PLACES_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")

            else -> throw IllegalArgumentException("Unknown URI: $uri");
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                PlaceContentProviderEntryPoint::class.java
            )

        return when (uriMatcher.match(uri)) {
            PlaceContract.CODE_PLACES_DIR -> {
                // Deleted rows counter will be always 0! Consider disable this way to update!
                // throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
                var localQuery = "DELETE FROM ${PlaceContract.TABLE_NAME}"

                selection?.takeIf { it.isNotEmpty() }?.let {
                    localQuery += " WHERE"
                    localQuery += " $it"
                } ?: throw IllegalArgumentException("Can not delete without selection!")

                val query = SimpleSQLiteQuery(localQuery, selectionArgs)
                val cursor = hiltEntryPoint.placeDao().rawOperation(query)
                appContext.contentResolver.notifyChange(uri, null)

                cursor?.count ?: 0
            }

            PlaceContract.CODE_PLACES_ITEM -> {
                val count = hiltEntryPoint.placeDao().deleteById(ContentUris.parseId(uri).toInt())
                appContext.contentResolver.notifyChange(uri, null)
                count
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                PlaceContentProviderEntryPoint::class.java
            )

        return when (uriMatcher.match(uri)) {
            PlaceContract.CODE_PLACES_DIR -> {
                // Updated rows counter will be always 0! Consider disable this way to update!
                // throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
                var localQuery = "UPDATE ${PlaceContract.TABLE_NAME}"

                selection?.takeIf { it.isNotEmpty() }?.let {
                    localQuery += it
                } ?: throw IllegalArgumentException("Can not delete without selection!")

                val query = SimpleSQLiteQuery(localQuery, selectionArgs)
                val cursor = hiltEntryPoint.placeDao().rawOperation(query)
                appContext.contentResolver.notifyChange(uri, null)

                cursor?.count ?: 0
            }

            PlaceContract.CODE_PLACES_ITEM -> {
                val place = PlaceEntity.fromContentValues(values)
                place.id = ContentUris.parseId(uri).toInt()
                val count = hiltEntryPoint.placeDao().update(place)
                appContext.contentResolver.notifyChange(uri, null)
                count
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface PlaceContentProviderEntryPoint {
        fun placeDao(): PlacesDao
    }
}