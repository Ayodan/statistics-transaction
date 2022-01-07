package com.transaction.statistics.service;

import com.transaction.statistics.model.Transaction;

public interface TransactionService {
    void addTransaction(Transaction transaction) throws Exception;
}
