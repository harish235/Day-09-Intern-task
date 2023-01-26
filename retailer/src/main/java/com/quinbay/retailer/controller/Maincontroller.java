package com.quinbay.retailer.controller;


import com.quinbay.retailer.functions.Retailerinterface;
import com.quinbay.retailer.functions.Retailerinventoryinterface;
import com.quinbay.retailer.model.Retailer;
import com.quinbay.retailer.model.Retailerinventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Maincontroller {

    @Autowired
    Retailerinterface retailerinterface;

    @Autowired
    Retailerinventoryinterface retailerinventoryinterface;


    @GetMapping("/retailer")
    public List<Retailer> getAllRetailer(){
        return retailerinterface.getAllRetailer();
    }

    @GetMapping("/retailer/{id}")
    public Optional<Retailer> getRetailerById(@PathVariable(value="id") Integer retailerid){
        return retailerinterface.getRetailer(retailerid);
    }

    @PostMapping("/createRetailer")
    public String createRetailer(@RequestBody Retailer r){
        return retailerinterface.createRetailer(r);
    }

    @DeleteMapping("/deleteRetailer")
    public ResponseEntity<HttpStatus> deleteRetailer(@RequestParam int r_id){
        return retailerinterface.deleteRetailer(r_id);
    }

    @GetMapping("/retailerstock")
    public List<Retailerinventory> getAllRetailerStock(){
        return retailerinventoryinterface.getAllRetailerStock();
    }

    @PutMapping("/updateRetailerStock")
    public String updateRetailerStock(@RequestBody Retailerinventory r, @RequestParam int amountpaid){
        return retailerinventoryinterface.updateRetailerStock(r, amountpaid);
    }

    @PutMapping("/payPendingAmount")
    public String payPendingAmoint(@RequestParam String txnCode, int amount){
        return retailerinventoryinterface.payPendingAmount(txnCode, amount);
    }

}
