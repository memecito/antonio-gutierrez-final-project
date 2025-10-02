package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "users")
public class ApiUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "created")
    private LocalDateTime createdAt= LocalDateTime.now();
    @Column(name = "active")
    private Boolean active= true;
    @Column(name = "admin")
    private Boolean admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "code")
    private Country country;
}
