package com.quinbay.retailer.service;


import com.quinbay.retailer.functions.Retailerinterface;
import com.quinbay.retailer.model.Retailer;
import com.quinbay.retailer.repository.Retailerrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Retailerservice implements Retailerinterface {

    @Autowired
    Retailerrepository retailerrepository;

    @Override
    @Cacheable(value = "listofretailers")
    public List<Retailer> getAllRetailer(){
        System.out.println("\n\tHitting getAllRetailer <-----------------");
        return retailerrepository.findAll();
    }

    @Override
    @Cacheable(value= "Retailer", key="#rid")
    public Optional<Retailer> getRetailer(int rid){
        System.out.println("\n\tHitting getRetailer <---------------");
        Optional<Retailer> retailerData = retailerrepository.findById(rid);
        return retailerData;

    }

    @Override
    public String createRetailer(Retailer r){
        try{
            Optional<Retailer> retailerData = retailerrepository.findByRetailercode(r.getRetailercode());
            if(retailerData.isPresent()) {
                return "Retailer already exists!!!";
            }else{
                retailerrepository.save(r);
                return "Retailer created successfully!!!";
            }
        }catch(Exception e){
            return "Retailer cannot be created!!!";
        }
    }

    @Override
    @CacheEvict(value= "Retailer", allEntries = false, key="#rid")
    public ResponseEntity<HttpStatus> deleteRetailer(int rid){
        System.out.println("\n\tHitting deleteRetailer <-----------------");
        try{
            retailerrepository.deleteById(rid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
