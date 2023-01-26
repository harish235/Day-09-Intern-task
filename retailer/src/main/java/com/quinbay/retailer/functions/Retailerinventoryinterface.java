package com.quinbay.retailer.functions;

import com.quinbay.retailer.model.Retailerinventory;

import java.util.List;

public interface Retailerinventoryinterface {

    List<Retailerinventory> getAllRetailerStock();

    String updateRetailerStock(Retailerinventory rs, int amount);

    String payPendingAmount(String txnCode, int amount);
}
