package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name",unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "country", orphanRemoval = true)
    private List<ApiUser> apiUsers;

    public Country(String code, String nombre) {
        this.code=code;
        this.name=nombre;
    }
}
