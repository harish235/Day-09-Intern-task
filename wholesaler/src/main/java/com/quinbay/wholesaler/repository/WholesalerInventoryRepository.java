package com.quinbay.wholesaler.repository;


import com.quinbay.wholesaler.model.WholesalerInventory;
import net.bytebuddy.description.type.TypeDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WholesalerInventoryRepository extends JpaRepository<WholesalerInventory, Integer> {

    WholesalerInventory findByWholesaleridAndWarehouseidAndProductid(String wh_id, String w_id, String p_id);

    List<WholesalerInventory> findByWholesaleridAndProductid(String wcode, String pcode);

    //List<WholesalerInventory> findByWholesaleridAndProductid(Sort.by("stock").descending());
}
