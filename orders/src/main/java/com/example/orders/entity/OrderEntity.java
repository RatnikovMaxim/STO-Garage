package com.example.orders.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
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
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    @AttributeOverride(name = "name", column = @Column(name = "user_name"))
    private UserEmbedded user;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "station_id"))
    @AttributeOverride(name = "name", column = @Column(name = "station_name"))
    private StationEmbedded station;
    @OneToMany(mappedBy = "order")
    private List<OrderPositionEntity> positions;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String status;
    @Column(
            insertable = false,
            updatable = false,
            nullable = false,
            columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP"
    )
    private Instant created;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserEmbedded {
        private long id;
        private String name;
    }

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class StationEmbedded {
        private long id;
        private String name;
    }
}
