package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "country", orphanRemoval = true)
    private List<ApiUser> apiUsers;

}
