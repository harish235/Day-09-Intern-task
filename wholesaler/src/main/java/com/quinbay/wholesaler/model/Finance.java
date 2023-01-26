package com.quinbay.wholesaler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finance {
    String buyertype;
    String buyercode;
    String sellercode;
    String productcode;
    int productprice;
    int qty;
    int amountpaid;
}
