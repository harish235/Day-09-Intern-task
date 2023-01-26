package com.quinbay.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    String buyertype;
    String buyercode;
    String sellercode;
    String productcode;
    String productname;
    int productprice;
    int qty;
    int amountpaid;
}
