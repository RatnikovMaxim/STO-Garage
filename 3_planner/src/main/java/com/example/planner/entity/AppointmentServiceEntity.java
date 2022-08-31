package com.example.planner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "appointment_services")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "service_id"))
    @AttributeOverride(name = "name", column = @Column(name = "service_name"))
    private ServiceEmbedded service;

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
