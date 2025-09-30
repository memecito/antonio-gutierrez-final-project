package com.nter.final_project.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_product")
public class OrderProduct {

    @EmbeddedId
    private OrderProductId orderProductId= new OrderProductId();

    private int amount;
}