package com.quinbay.wholesaler.controller;

import com.quinbay.wholesaler.functions.WholesalerInterface;
import com.quinbay.wholesaler.functions.WholesalerInventoryInterface;
import com.quinbay.wholesaler.model.Wholesaler;
import com.quinbay.wholesaler.model.WholesalerInventory;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Maincontroller {

    @Autowired
    WholesalerInterface wholesalerInterface;

    @Autowired
    WholesalerInventoryInterface wholesalerstockinterface;

    @GetMapping("/wholesaler")
    public List<Wholesaler> getAllWholesaler(){
        return wholesalerInterface.getAllWholesaler();
    }

    @GetMapping("/wholesaler/{id}")
    public Object getWholesalerById(@PathVariable(value="id") Integer wholesalerid){
        return wholesalerInterface.getWholesalerById(wholesalerid);
    }

    @PostMapping("/createWholesaler")
    public String createWholesaler(@RequestBody Wholesaler w){
        return wholesalerInterface.createWholesaler(w);
    }

    @DeleteMapping("/deleteWholesaler")
    public Object deleteWholesaler(@RequestParam String w_id){
        return wholesalerInterface.deleteWholesaler(w_id);
    }

    @PutMapping("/updateWarehouse")
    public Object updateWarehouse( @RequestParam int id,@RequestParam String email) {
        return wholesalerInterface.updateWarehouse(id,email);
    }

    @PutMapping("/updateWholesalerStock")
    public String updateWholesalerStock(@RequestBody WholesalerInventory w, @RequestParam int amountpaid){
        return wholesalerstockinterface.updateWholesalerStock(w, amountpaid);
    }

    @GetMapping("/wholesalerstock")
    public List<WholesalerInventory> getAllWholesalerStock(){
        return wholesalerstockinterface.getAllWholesalerStock();
    }

    @PutMapping("/payPendingAmount")
    public String payPendingAmoint(@RequestParam String txnCode, int amount){
        return wholesalerstockinterface.payPendingAmount(txnCode, amount);
    }

//    @GetMapping("/getStockFromWholesaler")
//    public List<WholesalerInventory> getStockFromWholesaler(@RequestParam String wcode, String pcode){
//        return wholesalerstockinterface.getStockFromWholesaler(wcode, pcode);
//    }

    @PutMapping("/reduceStockFromWholesaler")
    public int reduceStockFromWholesaler(@RequestParam String wCode, @RequestParam String pCode, @RequestParam int stock){
        return wholesalerstockinterface.reduceStockFromWholesaler(wCode, pCode, stock);
    }


}
