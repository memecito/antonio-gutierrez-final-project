package com.nter.final_project.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class Order {

    public Order(Long userId, String status, String created) {
        this.status = StatusOrder.valueOf(status);
        this.user = new ApiUser(userId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOrder status;
    @Column(name = "created")
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ApiUser user;

    @OneToMany(mappedBy = "orderProductId.order", orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

}
