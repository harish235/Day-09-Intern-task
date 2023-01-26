package com.example.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter @Getter @NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "code")
    String code;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phoneNumber")
    long phoneNumber;

    @Column(name = "location")
    String location;

}
