package com.hausberger.mysuperapp.framework.datasource.provider

import android.app.Activity
import android.content.AsyncQueryHandler
import android.net.Uri

class MyQueryHandler(activity: Activity) : AsyncQueryHandler(activity.contentResolver) {

    override fun onInsertComplete(token: Int, cookie: Any?, uri: Uri?) {
        super.onInsertComplete(token, cookie, uri)
    }
}