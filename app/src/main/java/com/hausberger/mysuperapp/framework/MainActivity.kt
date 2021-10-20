package com.hausberger.mysuperapp.framework

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.hausberger.mysuperapp.databinding.ActivityMainBinding
import com.hausberger.mysuperapp.framework.datasource.provider.contentprovider.PlaceContract
import com.hausberger.mysuperapp.framework.presentation.places.PlacesActivity
import com.hausberger.mysuperapp.framework.savedata.ui.SaveDataActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contentProviderButton.apply {
            setOnClickListener {
                val intent = Intent(this@MainActivity, PlacesActivity::class.java)
                startActivity(intent)
            }
        }

        binding.saveDataButton.apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, SaveDataActivity::class.java))
            }
        }

        //LoaderManager.getInstance(this).initLoader(5, null, loaderCallbacks)
        //insertToDb()
        //deleteFromDb()
        //updatePlace()
    }

    private val loaderCallbacks: LoaderManager.LoaderCallbacks<Cursor?> =
        object : LoaderManager.LoaderCallbacks<Cursor?> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {

                // A "projection" defines the columns that will be returned for each row
                val projection: Array<String> = arrayOf(
                    PlaceContract.COLUMN_ID,              // Contract class constant for the _ID column name
                    PlaceContract.COLUMN_TOWN,            // Contract class constant for the town column name
                    PlaceContract.COLUMN_COUNTRY          // Contract class constant for the country column name
                )

                // Defines a string to contain the selection clause
                var selectionClause: String? = null

                // Declares an array to contain selection arguments
                lateinit var selectionArgs: Array<String>

                // Gets a word from the UI
                val searchString: String? = "Hungary"

                // Remember to insert code here to check for invalid or malicious input.

                // If the word is the empty string, gets everything
                selectionArgs = searchString?.takeIf { it.isNotEmpty() }?.let {
                    selectionClause = "${PlaceContract.COLUMN_COUNTRY} = ?"
                    arrayOf(it)
                } ?: run {
                    selectionClause = null
                    emptyArray()
                }

                // Does a query against the table and returns a Cursor object
                // This query is analogous to the SQL statement:
                // SELECT _ID, display_name FROM contacts WHERE display_name = <userinput>;
                return CursorLoader(
                    applicationContext,
                    PlaceContract.URI_PLACE,        // The content URI of the words table
                    projection,                     // The columns to return for each row
                    selectionClause,                // Either null, or the word the user entered
                    selectionArgs,                  // Either empty, or the string the user entered
                    null
                )
            }

            override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
                data?.apply {
                    // Determine the column index of the column named "display_name"
                    val index: Int = getColumnIndex(PlaceContract.COLUMN_TOWN)

                    /*
                    * Moves to the next row in the cursor. Before the first movement in the cursor, the
                    * "row pointer" is -1, and if you try to retrieve data at that position you will get an
                    * exception.
                    */
                    while (moveToNext()) {
                        val townName = getString(index)
                        Log.d("TAG", "useContentProvider: ")
                    }
                }

                // Some providers return null if an error occurs, others throw an exception
                when (data?.count) {
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

            override fun onLoaderReset(loader: Loader<Cursor?>) {
                Log.d("TAG", "onLoaderReset: ")
                //mCheeseAdapter.setCheeses(null)
            }
        }
}