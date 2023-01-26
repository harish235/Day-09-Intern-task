package com.quinbay.retailer.repository;


import com.quinbay.retailer.model.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Retailerrepository extends JpaRepository<Retailer, Integer> {

    Optional<Retailer> findByRetailercode(String rcode);
}
