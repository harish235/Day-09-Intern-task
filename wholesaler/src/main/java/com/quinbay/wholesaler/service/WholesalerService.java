package com.quinbay.wholesaler.service;
import com.quinbay.wholesaler.functions.WholesalerInterface;
import com.quinbay.wholesaler.model.Wholesaler;
import com.quinbay.wholesaler.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WholesalerService implements WholesalerInterface {

    @Autowired
    WholesalerRepository wholesalerRepository;

    @Override
    @Cacheable(value = "listofwholesalers")
    public List<Wholesaler> getAllWholesaler(){
        System.out.println("Hitting get ALL WHOLESALER   <---------------");
        return wholesalerRepository.findAll();
    }


    @Override
    @Cacheable(value= "Wholesaler", key="#wid")
    public Object getWholesalerById(int wid){
        Optional<Wholesaler> wholesalerdata = wholesalerRepository.findById(wid);
        System.out.println("Hitting get Wholesaler   <---------------");
        if(wholesalerdata.isPresent()){
            return wholesalerdata;
        }
        return "does not exist!!!";
    }

    @Override
    public String createWholesaler(Wholesaler w){
        Optional<Wholesaler> wholesalerdata = wholesalerRepository.findByWholesalercode(w.getWholesalercode());
        try{
            if(wholesalerdata.isPresent()){
                return "Wholesaler with that ID already exists!!!";
            }
            wholesalerRepository.save(w);
            return "Wholesaler created successfully!!!";
        } catch(Exception e){
            return "Wholesaler cannot be created!!!";
        }
    }

    @Override
    @CacheEvict(value= "Wholesaler", key="#wid")
    public Object deleteWholesaler(String wid){
        try{
            System.out.println("\n\t Hitting delete wholesaler    <-----------");
//            wholesalerRepository.deleteByWholesalercode(wid);
            wholesalerRepository.deleteById(wid);
            return "Wholesaler deleted";
        } catch(Exception e){
            return "Cannot be deleted!!!!";
        }
    }

    @Override
    @CachePut(value = "Wholesaler", key = "#id")
    public Object updateWarehouse(int id, String email) {
        System.out.println("Searching for warehouses");
        Optional<Wholesaler> opt = wholesalerRepository.findById(id);
        if (opt.isPresent()) {
            opt.get().setEmail(email);
            wholesalerRepository.save(opt.get());
            return opt.get();
        }
        return "Warehouse id doesn't exist";
    }
}
