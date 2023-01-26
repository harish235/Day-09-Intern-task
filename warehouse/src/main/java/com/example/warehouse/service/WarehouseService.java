package com.example.warehouse.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.warehouse.functions.WarehouseInterface;
import com.example.warehouse.model.Product;
import com.example.warehouse.model.Warehouse;
import com.example.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WarehouseService implements WarehouseInterface {
    @Autowired
   WarehouseRepository warehouseRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());


    @Override
    @Cacheable(value = "Warehouses")
    public List<Warehouse> findAllWarehouse() {
        System.out.println("\n\tHitting list of Warehouses");
        return (List<Warehouse>) warehouseRepository.findAll();
    }

    @Override
    @Cacheable(value = "Warehouse", key = "#id")
    public Object findWarehouseByID(int id) {
        LOG.info("Getting user with ID {}.", id);
        Optional<Warehouse> opt = warehouseRepository.findById(id);
       // kafkaPublishingService.productInfo(opt.get());
        System.out.println("Finding product...");
        if (opt.isPresent()) {
            System.out.println("Product present");
            return opt;
        } else
            return "Warehouse does not exists!!!!";

    }

    @Override
    public String addWarehouse(Warehouse inputWarehouse) {
        Optional<Warehouse> warehouse = warehouseRepository.findByCode(inputWarehouse.getCode());
        try{
            if(warehouse.isPresent()){
                return "Warehouse id already exist";
            }
            warehouseRepository.save(inputWarehouse);
            return "Warehouse created successfully!!!";
        } catch(Exception e){
            return "Warehouse cannot be created!!!";
        }
    }

    @Override
    public String deleteAllWarehouse() {

        warehouseRepository.deleteAll();
        return "Warehouse entries are deleted";
    }

    @Override
    @CacheEvict(value = "Warehouse", key = "#id")
    public String deleteWarehouseById(int id) {

            Optional<Warehouse> warehouse = warehouseRepository.findById(id);
            if (warehouse != null) {
                warehouseRepository.deleteById(id);
                return "Warehouse deleted";
            }
            else {
                return  "Warehouse not present";
            }
        }


    @Override
    @CachePut(value = "Warehouse", key = "#id")
    public Object updateWarehouse(int id, long phoneNumber) {
        System.out.println("Searching for warehouses");
        Optional<Warehouse> opt = warehouseRepository.findById(id);
        if (opt.isPresent()) {
            opt.get().setPhoneNumber(phoneNumber);
            warehouseRepository.save(opt.get());
            return opt.get();
        }
        return "Warehouse id doesn't exist";
    }

}
