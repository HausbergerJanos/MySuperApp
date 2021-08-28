package com.hausberger.mysuperapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        useContentProvider()
    }

    private fun useContentProvider() {
        // Check the SDK version and whether the permission is already granted or not.
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                554
            )
        }


        // A "projection" defines the columns that will be returned for each row
        val projection: Array<String> = arrayOf(
            ContactsContract.Contacts._ID,              // Contract class constant for the _ID column name
            ContactsContract.Contacts.DISPLAY_NAME      // Contract class constant for the contact column name
        )

        // Defines a string to contain the selection clause
        var selectionClause: String? = null

        // Declares an array to contain selection arguments
        lateinit var selectionArgs: Array<String>

        // Gets a word from the UI
        val searchString: String? = null

        // Remember to insert code here to check for invalid or malicious input.

        // If the word is the empty string, gets everything
        selectionArgs = searchString?.takeIf { it.isNotEmpty() }?.let {
            selectionClause = "${ContactsContract.Contacts.DISPLAY_NAME} = ?"
            arrayOf(it)
        } ?: run {
            selectionClause = null
            emptyArray()
        }

        // Does a query against the table and returns a Cursor object
        // This query is analogous to the SQL statement:
        // SELECT _ID, display_name FROM contacts WHERE display_name = <userinput>;
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,      // The content URI of the words table
            projection,                                 // The columns to return for each row
            selectionClause,                            // Either null, or the word the user entered
            selectionArgs,                              // Either empty, or the string the user entered
            null                        // The sort order for the returned rows
        )

        cursor?.apply {
            // Determine the column index of the column named "display_name"
            val index: Int = getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

            /*
            * Moves to the next row in the cursor. Before the first movement in the cursor, the
            * "row pointer" is -1, and if you try to retrieve data at that position you will get an
            * exception.
            */
            while (moveToNext()) {
                val contactName = getString(index)
            }
        }

        // Some providers return null if an error occurs, others throw an exception
        when (cursor?.count) {
            null -> {
                /*
                 * Insert code here to handle the error. Be sure not to use the cursor!
                 * You may want to call android.util.Log.e() to log this error.
                 */
                Log.e("TAG", "useContentProvider: ")
            }

            0 -> {
                /*
                 * Insert code here to notify the user that the search was unsuccessful. This isn't
                 * necessarily an error. You may want to offer the user the option to insert a new
                 * row, or re-type the search term.
                 */
                Log.d("TAG", "useContentProvider: ")
            }

            else -> {
                // Insert code here to do something with the results
                Log.d("TAG", "useContentProvider: ")
            }
        }
    }
}