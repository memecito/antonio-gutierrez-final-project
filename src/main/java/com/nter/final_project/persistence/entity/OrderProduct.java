package com.nter.final_project.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "ordersproducts")
public class OrderProduct {

    @Column(name = "amount")
    private int amount;
}
