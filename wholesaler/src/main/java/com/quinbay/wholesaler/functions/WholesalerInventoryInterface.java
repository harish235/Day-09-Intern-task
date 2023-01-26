package com.quinbay.wholesaler.functions;

import com.quinbay.wholesaler.model.WholesalerInventory;

import java.util.List;

public interface WholesalerInventoryInterface {

    String updateWholesalerStock(WholesalerInventory ws, int amount);

    String payPendingAmount(String txnCode, int amount);

    List<WholesalerInventory> getAllWholesalerStock();

//    List<WholesalerInventory> getStockFromWholesaler(String wcode, String pcode);

    int reduceStockFromWholesaler(String wCode, String pCode, int stock);

}
