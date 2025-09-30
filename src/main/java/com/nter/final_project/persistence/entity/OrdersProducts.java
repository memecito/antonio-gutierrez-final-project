package com.nter.final_project.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "ordersproducts")
public class OrdersProducts {
    @EmbeddedId
    private OrderProductId orderProductId= new OrderProductId();

    private int amount;
}
