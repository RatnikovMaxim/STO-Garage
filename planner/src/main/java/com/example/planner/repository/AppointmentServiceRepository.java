package com.example.planner.repository;

import com.example.planner.entity.AppointmentServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentServiceRepository extends JpaRepository<AppointmentServiceEntity, Long> {
}
