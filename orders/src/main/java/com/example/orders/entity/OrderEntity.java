package com.example.orders.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ElementCollection
    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "user_id"))
    private List<long> ownerId;
    private List<long> stoId;
    private List<long> serviceId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String status;
}
