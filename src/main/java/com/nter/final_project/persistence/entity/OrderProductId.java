package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Data
@Embeddable
public class OrderProductId implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order", nullable = false)
    private Order order;
}
