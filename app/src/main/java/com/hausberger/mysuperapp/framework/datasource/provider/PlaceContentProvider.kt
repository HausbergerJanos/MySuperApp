package com.hausberger.mysuperapp.framework.datasource.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            EntryPointAccessors.fromApplication(appContext, PlaceContentProviderEntryPoint::class.java)

        val code = uriMatcher.match(uri)
        if (code == PlaceContract.CODE_PLACES_DIR || code == PlaceContract.CODE_PLACES_ITEM) {

            var cursor: Cursor? = if (code == PlaceContract.CODE_PLACES_DIR) {
                hiltEntryPoint.placeDao().selectAll()
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

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface PlaceContentProviderEntryPoint {
        fun placeDao(): PlacesDao
    }
}