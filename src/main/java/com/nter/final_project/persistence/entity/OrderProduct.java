package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "ordersproducts")
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id = new OrderProductId();

    @Column(name = "amount")
    private Integer amount;

    //RELATIONS
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
}
