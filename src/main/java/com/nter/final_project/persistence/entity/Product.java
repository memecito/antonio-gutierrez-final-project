package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime created= LocalDateTime.now();

    @OneToMany(mappedBy = "orderProductId.product", orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

    public Product(String laptopProX15, double v, String inStock, String s) {
    }

    //RELATIONS

}
