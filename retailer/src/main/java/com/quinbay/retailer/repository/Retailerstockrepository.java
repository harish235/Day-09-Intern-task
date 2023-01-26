package com.quinbay.retailer.repository;


import com.quinbay.retailer.model.Retailerinventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Retailerstockrepository extends JpaRepository<Retailerinventory, Integer> {

//    Retailerinventory

//    Retailerinventory findByRetaileridAndProductidAndWholesalerid(String r_id, String p_id, String w_id);

    Retailerinventory findByRetailercodeAndProductcodeAndWholesalercode(String rcode, String pcode, String wcode);
}
