package com.quinbay.wholesaler.model;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wholesaler")
public class Wholesaler implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="wholesalercode")
    String wholesalercode;

    @Column(name="name")
    String wholesalername;

    @Column(name="email")
    String email;

    @Column(name="address")
    String wholesaleraddress;

    @Column(name="phone")
    String wholesalerphone;
}
