package com.quinbay.finance.api;

import com.quinbay.finance.model.Transaction;

import java.io.FileNotFoundException;

public interface BillService {
    String generatePdf(Transaction t) throws FileNotFoundException;
}
