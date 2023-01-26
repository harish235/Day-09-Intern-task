package com.quinbay.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="finance")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="txncode")
    String txncode;

    @Column(name="buyertype")
    String buyertype;

    @Column(name="buyercode")
    String buyercode;

    @Column(name="sellercode")
    String sellercode;

    @Column(name="productcode")
    String productcode;

    @Column(name="productprice")
    int productprice;

    @Column(name="productqty")
    int productqty;

    @Column(name="totalamount")
    int totalamount;

    @Column(name="balance")
    int balance;

    @Column(name = "date")
    Date date;
}
