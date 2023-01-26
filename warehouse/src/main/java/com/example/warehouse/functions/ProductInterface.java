package com.example.warehouse.functions;

import com.example.warehouse.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductInterface {
    List<Product> findAllProduct();
    Object findProductByID(int id);
    String addProduct(Product inputProduct);
    String deleteAllProduct();
    String deleteProductById(int id);
    String updateProduct(int id, int price);
}
