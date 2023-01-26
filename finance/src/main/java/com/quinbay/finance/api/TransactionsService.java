package com.quinbay.finance.api;

import com.quinbay.finance.model.Payment;
import com.quinbay.finance.model.Transaction;

import java.util.List;

public interface TransactionsService {
    String enterPaymentInfo(Payment f);
    List<Transaction> viewTransactions();
    Object getTransaction(int id);
    String payPendingAmount(String txncode, int amount);
}
