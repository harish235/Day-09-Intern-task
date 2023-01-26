package com.quinbay.wholesaler.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wholesalerinventory")
public class WholesalerInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="wholesalerid")
    String wholesalerid;

    @Column(name ="warehouseid")
    String warehouseid;

    @Column(name="productid")
    String productid;

    @Column(name="price")
    int productprice;

    @Column(name="stock")
    int stock;

    @Column(name="date")
    String date;
}
