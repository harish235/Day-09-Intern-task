package com.quinbay.retailer.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="retailerinventory")
public class Retailerinventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="retailercode")
    String retailercode;

    @Column(name ="wholesalercode")
    String wholesalercode;

    @Column(name="productcode")
    String productcode;

    @Column(name="price")
    int productprice;

    @Column(name="stock")
    int stock;

    @Column(name="date")
    String date;
}
