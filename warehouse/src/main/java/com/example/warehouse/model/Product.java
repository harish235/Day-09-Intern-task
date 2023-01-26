package com.example.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class Product  {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;

        @Column(name = "code")
        String code;

        @Column(name = "name")
        String name;

        @Column(name = "description")
        String description;

        @Column(name = "price")
        int price;

        @Column(name = "gst")
        double gst;
}
