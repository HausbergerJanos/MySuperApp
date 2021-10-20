package com.hausberger.mysuperapp.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hausberger.mysuperapp.framework.datasource.cache.model.UnsyncedTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsyncedTransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: UnsyncedTransactionEntity): Long

    @Query("""
        SELECT * FROM pending_transactions
        WHERE entity_id = :id
    """)
    suspend fun getPendingTransactionByEntityId(id: Int): UnsyncedTransactionEntity?

    @Query("""
        SELECT * FROM pending_transactions
        WHERE table_name = :tableName
    """)
    suspend fun getPendingTransactions(tableName: String): List<UnsyncedTransactionEntity>

    @Query("""
        DELETE FROM pending_transactions 
        WHERE _id = :id
    """)
    suspend fun delete(id: Int): Int
}