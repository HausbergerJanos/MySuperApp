package com.hausberger.mysuperapp.framework.datasource.provider.contentprovider

import android.app.Activity
import android.content.AsyncQueryHandler
import android.net.Uri

class MyQueryHandler(activity: Activity) : AsyncQueryHandler(activity.contentResolver) {

    override fun onInsertComplete(token: Int, cookie: Any?, uri: Uri?) {
        super.onInsertComplete(token, cookie, uri)
    }

    override fun onDeleteComplete(token: Int, cookie: Any?, result: Int) {
        super.onDeleteComplete(token, cookie, result)
    }

    override fun onUpdateComplete(token: Int, cookie: Any?, result: Int) {
        super.onUpdateComplete(token, cookie, result)
    }
}