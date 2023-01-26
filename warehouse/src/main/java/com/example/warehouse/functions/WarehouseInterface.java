package com.example.warehouse.functions;

import com.example.warehouse.model.Warehouse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WarehouseInterface {
    List<Warehouse> findAllWarehouse();
    Object findWarehouseByID(int id);
    String addWarehouse(Warehouse inputWarehouse);
    String deleteAllWarehouse();
    String deleteWarehouseById(int id);
    Object updateWarehouse(int id, long phoneNumber);
}
