package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "ordersproducts")
public class OrderProduct {

    //todo crear las realciones OneToMany con order y product
    //todo establecer clave primaria con las dos relaciones

    @EmbeddedId
    @Column(name = "amount")
    private int amount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;
}
