package com.example.warehouse.repository;


import com.example.warehouse.model.Product;
import com.example.warehouse.model.WarehouseInventory;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory,Integer> {
    Optional<WarehouseInventory> findByWarehousecode(String code);

    WarehouseInventory findByWarehousecodeAndProductcode(String warehousecode, String productcode);
}
