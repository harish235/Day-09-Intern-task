package com.quinbay.wholesaler.functions;

import com.quinbay.wholesaler.model.Wholesaler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface WholesalerInterface {

    List<Wholesaler> getAllWholesaler();

//    Optional<Wholesaler> getWholesalerById(int w_id);

    Object getWholesalerById(int w_id);

    String createWholesaler(Wholesaler w);

    Object deleteWholesaler(String code);

    Object updateWarehouse(int wid, String email);
}
