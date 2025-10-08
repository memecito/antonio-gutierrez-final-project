package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class ApiUser {

    public ApiUser(String fullName, String email, String password, String created, boolean active, String code, boolean admin) {
        this.fullName=fullName;
        this.email=email;
        this.password=password;
        this.createdAt= LocalDateTime.now();
        this.active=active;
        this.admin=admin;
        this.country.setCode(code);

    }

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
    private Boolean admin= false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "code")
    private Country country;


}
