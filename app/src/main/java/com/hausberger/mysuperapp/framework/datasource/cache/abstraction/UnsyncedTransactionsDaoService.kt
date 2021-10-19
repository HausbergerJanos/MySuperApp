package com.hausberger.mysuperapp.framework.datasource.cache.abstraction

import com.hausberger.mysuperapp.framework.datasource.cache.model.UnsyncedTransactionEntity
import kotlinx.coroutines.flow.Flow

interface UnsyncedTransactionsDaoService {

    suspend fun insert(transaction: UnsyncedTransactionEntity): Long

    suspend fun getPendingTransactions(table: String): List<UnsyncedTransactionEntity>

    suspend fun deleteTransaction(id: Int): Int
}