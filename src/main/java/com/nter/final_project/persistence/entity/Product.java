package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Enumerated
    @Column(name = "status")
    private Status status;
    @Column(name = "created")
    private LocalDate createdAt;


}
