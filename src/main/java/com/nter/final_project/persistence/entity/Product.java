package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProduct status;
    @Column(name = "created")
    private LocalDateTime createdAt;

    //RELATIONS
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orders;

}
