package com.example.orders.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_positions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderPositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "service_id"))
    @AttributeOverride(name = "name", column = @Column(name = "service_name"))
    private ServiceEmbedded service;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private long price;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ServiceEmbedded {
        private long id;
        private String name;
    }
}
