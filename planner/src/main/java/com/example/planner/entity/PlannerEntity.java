package com.example.planner.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "planner")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//
//    @ManyToOne
//    private UserEntity user;
//
//    @ManyToOne
//    private StationEntity station;

    @Column(
            insertable = true,
            updatable = true,
            nullable = false,
            columnDefinition = "TIMESTAMPTZ NOT NULL"
    )
    private Instant booking_time;

    @Column(
            insertable = false,
            updatable = false,
            nullable = false,
            columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP"
    )
    private Instant created;
}
