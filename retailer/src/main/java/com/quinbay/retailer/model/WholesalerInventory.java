package com.quinbay.retailer.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WholesalerInventory {
    int id;

    String wholesalerid;

    String warehouseid;

    String productid;

    int productprice;

    int stock;

    String date;
}
