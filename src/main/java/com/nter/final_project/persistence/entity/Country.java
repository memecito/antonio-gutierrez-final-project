package com.nter.final_project.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "code", unique = true, nullable = false)
    private Integer code;

    @Column(name = "name", nullable = false)
    private String name;

}
