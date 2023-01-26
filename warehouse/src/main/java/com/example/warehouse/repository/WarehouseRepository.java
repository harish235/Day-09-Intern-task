package com.example.warehouse.repository;

import com.example.warehouse.model.Product;
import com.example.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {
    // ArrayList<Product> findAllProduct();
    Optional<Warehouse> findByCode(String code);


}