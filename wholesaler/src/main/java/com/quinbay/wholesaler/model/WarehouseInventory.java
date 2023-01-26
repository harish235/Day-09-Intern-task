package com.quinbay.wholesaler.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseInventory {
    int id;
    String warehousecode;
    String productcode;
    String productname;
    int stock;
    int price;
}
