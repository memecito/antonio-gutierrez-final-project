package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOrder status;
    @Column(name = "created")
    private LocalDateTime createdAt;

    //RELATIONS
    /*
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProducts;

     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ApiUser user;

    @OneToMany(mappedBy = "orderProductId.order", orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

}
