package com.quinbay.retailer.functions;

import com.quinbay.retailer.model.Retailer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface Retailerinterface {

    List<Retailer> getAllRetailer();

    Optional<Retailer> getRetailer(int r_id);

    String createRetailer(Retailer r);

    ResponseEntity<HttpStatus> deleteRetailer(int id);
}
