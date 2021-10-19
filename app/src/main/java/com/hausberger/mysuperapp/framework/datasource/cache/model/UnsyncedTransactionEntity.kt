package com.hausberger.mysuperapp.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hausberger.mysuperapp.framework.datasource.provider.contentprovider.PlaceContract

@Entity(tableName = "pending_transactions")
data class UnsyncedTransactionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PlaceContract.COLUMN_ID, index = true)
    var id: Int = 0,

    @ColumnInfo(name = "entity_id")
    val entityId: Int,

    @ColumnInfo(name = "table_name")
    val entityTableName: String,

    @ColumnInfo(name = "transaction_type")
    val transactionType: Int
)