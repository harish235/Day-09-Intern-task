package com.example.warehouse.functions;

import com.example.warehouse.model.Warehouse;
import com.example.warehouse.model.WarehouseInventory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WarehouseInventoryInterface {
    List<WarehouseInventory> findAllWarehouseInventory();
    ResponseEntity<WarehouseInventory> findWarehouseInventoryByID(int id);
    String addWarehouseInventory(String warehouseCode,String productCode,int stock,int price);
    String deleteAllWarehouseInventory();
    String deleteWarehouseInventoryById(int id);
    WarehouseInventory getStockFromWarehouse(String wcode, String pcode);
    String reduceStockFromWarehouse(String wcode, String pcode, int stock);

}
