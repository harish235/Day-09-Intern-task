package com.example.warehouse.service;

import com.example.warehouse.functions.ProductInterface;
import com.example.warehouse.model.Product;
import com.example.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService implements ProductInterface {
    @Autowired
    ProductRepository productRepository;



    @Override
    @Cacheable(value="products")
    public List<Product> findAllProduct() {
        System.out.println("\n\tHitting findAll Products <---------------");
        return (List<Product>) productRepository.findAll();
    }



    @Override
    @Cacheable(value = "product", key = "#id")
    public Object findProductByID(int id) {
        Optional<Product> opt = productRepository.findById(id);
        System.out.println("Finding product...");
        if (opt.isPresent()) {
            System.out.println("Product present");
            return opt;
        } else
            return "does not exist!!!!";

    }


    @Override
    public String addProduct(Product inputProduct) {
        Optional<Product> product = productRepository.findByCode(inputProduct.getCode());
        try{
            if(product.isPresent()){
                return "Product id already exist";
            }
            productRepository.save(inputProduct);
            return "Product created successfully!!!";
        } catch(Exception e){
            return "Product cannot be created!!!";
        }


    }

    @Override
    public String deleteAllProduct() {

        productRepository.deleteAll();
        return "Product entries are deleted";
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    public String deleteProductById(int id) {
            Optional<Product> product = productRepository.findById(id);
            if (product != null) {

                productRepository.deleteById(id);
                return "Product deleted";
            } else {
                return "Product id doesn't exist";
            }
    }

    @Override
    public String updateProduct(int id, int price) {
        System.out.println("Searching for products");
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            opt.get().setPrice(price);
            productRepository.save(opt.get());
            return "Product data updated successfully.........";
        }
        return "Product id doesn't exist";
    }


}

