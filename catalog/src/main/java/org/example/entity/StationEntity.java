package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;
    @ManyToMany
    @JoinTable(name = "station_services",
            joinColumns =
            @JoinColumn(name = "station_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "service_id", referencedColumnName = "id"))
    private List<ServiceEntity> services;
}
