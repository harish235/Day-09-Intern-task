package com.example.warehouse.controller;

import com.example.warehouse.functions.ProductInterface;
import com.example.warehouse.functions.WarehouseInterface;
import com.example.warehouse.functions.WarehouseInventoryInterface;
import com.example.warehouse.model.Product;
import com.example.warehouse.model.Warehouse;
import com.example.warehouse.model.WarehouseInventory;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    @Autowired
    ProductInterface productInterface;

    @Autowired
    WarehouseInterface warehouseInterface;

    @Autowired
    WarehouseInventoryInterface warehouseInventoryInterface;

    @PostMapping("/addProduct")
    public String postProductData(@RequestBody Product inputProduct)
    {
        return productInterface.addProduct(inputProduct);
    }

    @GetMapping("/findAllProducts")
    public List<Product> getAllProduct() {

        return productInterface.findAllProduct();
    }

    @GetMapping("/findProductById/{id}")
    public Object getProductUsingId(@PathVariable int id) {

        return productInterface.findProductByID(id);
    }


    @DeleteMapping("/deleteByProductId/{id}")
    public String deleteProductUsingId(@PathVariable int id){

        return productInterface.deleteProductById(id);
    }

    @DeleteMapping("/deleteAllProduct")
    public void deleteProduct() {

        productInterface.deleteAllProduct();
    }

    @PutMapping("/updateProduct/{id}/{price}")
    public String updateProduct( @PathVariable int id,@PathVariable int price) {
        return productInterface.updateProduct(id,price);
    }

    @PostMapping("/addWarehouse")
    public String postWarehouseData(@RequestBody Warehouse inputWarehouse)
    {
        return warehouseInterface.addWarehouse(inputWarehouse);
    }

    @GetMapping("/findAllWarehouse")
    public List<Warehouse> getAllWarehouse() {

        return  warehouseInterface.findAllWarehouse();
    }

    @GetMapping("/findWarehouseById/{id}")
    public Object getWarehouseUsingId(@PathVariable int id) {
        return warehouseInterface.findWarehouseByID(id);
    }

    @DeleteMapping("/deleteWarehouseById/{id}")
    public String deleteWarehouseUsingId(@PathVariable(value = "id") Integer id){
        return warehouseInterface.deleteWarehouseById(id);
    }

    @DeleteMapping("/deleteAllWarehouse")
    public String deleteWarehouse() {
        return warehouseInterface.deleteAllWarehouse();

    }

    @PutMapping("/updateWarehouse")
    public Object updateWarehouse( @RequestParam int id,@RequestParam long phoneNumber) {
        return warehouseInterface.updateWarehouse(id,phoneNumber);
    }

    @PutMapping("/updateWarehouseInventory")
    public String postData( @RequestParam String warehouseCode, @RequestParam String productCode,
                           @RequestParam int stock,@RequestParam int price)
    {
        return warehouseInventoryInterface.addWarehouseInventory(warehouseCode,productCode,stock,price);
    }

    @GetMapping("/findAllWarehouseInventory")
    public List<WarehouseInventory> getAllWarehouseInventory() {
        return  warehouseInventoryInterface.findAllWarehouseInventory();
    }

    @GetMapping("/findWarehouseInventoryById/{id}")
    public ResponseEntity<WarehouseInventory> getWarehouseInventoryUsingId(@PathVariable int id) {
        return warehouseInventoryInterface.findWarehouseInventoryByID(id);
    }


    @GetMapping("/getStockFromWarehouse")
    public WarehouseInventory getStockFromWarehouse(@RequestParam String productid, @RequestParam String warehouseid){
        return warehouseInventoryInterface.getStockFromWarehouse(productid, warehouseid);
    }

    @PutMapping("/reduceStockFromWarehouse")
    public String reduceStockFromWarehouse(@RequestParam String warehousecode, @RequestParam String productcode, @RequestParam int stock){
        return warehouseInventoryInterface.reduceStockFromWarehouse(warehousecode, productcode, stock);
    }
}
