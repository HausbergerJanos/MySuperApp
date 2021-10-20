package com.hausberger.mysuperapp.framework.datasource.cache.implementation

import com.hausberger.mysuperapp.framework.datasource.cache.abstraction.UnsyncedTransactionsDaoService
import com.hausberger.mysuperapp.framework.datasource.cache.database.UnsyncedTransactionsDao
import com.hausberger.mysuperapp.framework.datasource.cache.model.UnsyncedTransactionEntity
import javax.inject.Inject

class UnsyncedTransactionsDaoServiceImpl
@Inject
constructor(
    private val unsyncedTransactionsDao: UnsyncedTransactionsDao
) : UnsyncedTransactionsDaoService {

    override suspend fun insert(transaction: UnsyncedTransactionEntity): Long {
        return unsyncedTransactionsDao.insert(transaction)
    }

    override suspend fun getPendingTransactionByEntityId(id: String): UnsyncedTransactionEntity? {
        return unsyncedTransactionsDao.getPendingTransactionByEntityId(id)
    }

    override suspend fun getPendingTransactions(table: String): List<UnsyncedTransactionEntity> {
        return unsyncedTransactionsDao.getPendingTransactions(table)
    }

    override suspend fun deleteTransaction(id: Int): Int {
        return unsyncedTransactionsDao.delete(id)
    }
}