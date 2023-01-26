package com.quinbay.retailer.model;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "retailer")
public class Retailer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="retailercode")
    String retailercode;

    @Column(name="name")
    String retailername;

    @Column(name="email")
    String email;

    @Column(name="address")
    String retaileraddress;

    @Column(name="phone")
    String retailerphone;

}
