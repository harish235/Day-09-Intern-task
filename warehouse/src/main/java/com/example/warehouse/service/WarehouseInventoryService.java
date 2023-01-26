package com.example.warehouse.service;

import com.example.warehouse.functions.WarehouseInterface;
import com.example.warehouse.functions.WarehouseInventoryInterface;
import com.example.warehouse.model.Product;
import com.example.warehouse.model.Warehouse;
import com.example.warehouse.model.WarehouseInventory;
import com.example.warehouse.repository.ProductRepository;
import com.example.warehouse.repository.WarehouseInventoryRepository;
import com.example.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseInventoryService implements WarehouseInventoryInterface {
    @Autowired
    WarehouseInventoryRepository warehouseInventoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Override
    @Cacheable(value = "Warehouseinventories")
    public List<WarehouseInventory> findAllWarehouseInventory() {
        return (List<WarehouseInventory>) warehouseInventoryRepository.findAll();
    }

    @Override
    @Cacheable(value = "Warehouseinventory", key = "#id")
    public ResponseEntity<WarehouseInventory> findWarehouseInventoryByID(int id) {
        Optional<WarehouseInventory> opt = warehouseInventoryRepository.findById(id);
        // kafkaPublishingService.productInfo(opt.get());
        System.out.println("Finding product...");
        if (opt.isPresent()) {
            System.out.println("Product present");
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        } else
            return new ResponseEntity<>(opt.get(), HttpStatus.NOT_FOUND);

    }

    @Override
    public String addWarehouseInventory(String warehouseCode,String productCode,int stock,int price) {
        Optional<Product> tempProduct = productRepository.findByCode(productCode);
        Optional<Warehouse> tempWarehouse = warehouseRepository.findByCode(warehouseCode);
        try{
            if(tempWarehouse.isPresent()){
                WarehouseInventory newStock = warehouseInventoryRepository.findByWarehousecodeAndProductcode(warehouseCode,productCode);

                if (newStock != null) {
                    newStock.setPrice(price);
                    newStock.setStock(stock + newStock.getStock());
                    warehouseInventoryRepository.save(newStock);

                } else {
                    newStock = new WarehouseInventory();
                    newStock.setWarehousecode(warehouseCode);
                    newStock.setProductcode(productCode);
                    newStock.setStock(stock);
                    newStock.setPrice(price);
                    newStock.setProductname(tempProduct.get().getName());
                  //  newStock.setId(w.getId());

                    warehouseInventoryRepository.save(newStock);
                }
                return "Warehouse inventory updated successfully!!!";

            }
            else{
                return "Warehouse with that id does not exist";

            }

        } catch(Exception e){
            return "Warehouse cannot be created!!!";
        }
    }



    @Override
    public String deleteAllWarehouseInventory() {

        warehouseInventoryRepository.deleteAll();
        return "Warehouse Inventory entries are deleted";
    }

    @Override
    @CacheEvict(value = "Warehouseinventory", key = "#id", allEntries=true)
    public String deleteWarehouseInventoryById(int id) {

        Optional<WarehouseInventory> warehouse = warehouseInventoryRepository.findById(id);
        if (warehouse != null) {
            warehouseInventoryRepository.deleteById(id);
            return "Warehouse deleted";
        }
        else {
            return  "Warehouse not present";
        }
    }

    @Override
    public WarehouseInventory getStockFromWarehouse(String pcode, String wcode){
        System.out.println("\n\tEntered get stock!!!!!!!!!!!!!!!");
        return warehouseInventoryRepository.findByWarehousecodeAndProductcode(wcode,pcode);
    }

    @Override
    public String reduceStockFromWarehouse(String wcode, String pcode, int stock){
        System.out.println("\n\tEntered reduce stock!!!!!!!!!!!!!!!");
        WarehouseInventory temp = warehouseInventoryRepository.findByWarehousecodeAndProductcode(wcode, pcode);
        temp.setStock(temp.getStock() - stock);
        warehouseInventoryRepository.save(temp);
        return "success";
    }

}
