package com.hausberger.mysuperapp.framework.datasource.provider

import android.net.Uri
import android.provider.BaseColumns

interface PlaceContract {

    companion object {
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_COUNTRY = "country"
        public const val TABLE_NAME = "places"

        /** The authority of this content provider. */
        val AUTHORITY = "com.hausberger.mysuperapp.provider"

        /** The URI for the Places table. */
        val URI_PLACE = Uri.parse(
            "content://$AUTHORITY/${PlaceContract.TABLE_NAME}"
        )
		
		/** The URI for the Places table. */
        val URI_PLACE_TEST = Uri.parse(
            "content://$AUTHORITY/${PlaceContract.TABLE_NAME}/Debrecen"
        )

        /** The match code for some items in the Places table.  */
        const val CODE_PLACES_DIR = 1

        /** The match code for an item in the Places table.  */
        const val CODE_PLACES_ITEM = 2
    }
}