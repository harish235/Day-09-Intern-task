package com.quinbay.wholesaler.repository;


import com.quinbay.wholesaler.model.Wholesaler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WholesalerRepository extends JpaRepository<Wholesaler, Integer> {

    Optional<Wholesaler> findByWholesalercode(String wcode);
    void deleteByWholesalercode(String whc);

    void deleteById(String wid);
}
