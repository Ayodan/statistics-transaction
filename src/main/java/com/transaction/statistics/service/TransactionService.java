package com.transaction.statistics.service;

import com.transaction.statistics.model.Transaction;
import com.transaction.statistics.model.TransactionReport;

public interface TransactionService {
    void addTransaction(Transaction transaction) throws Exception;
    TransactionReport getTransactionReport();
}
