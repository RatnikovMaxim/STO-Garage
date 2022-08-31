package com.example.planner.repository;

import com.example.planner.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findAllByStationIdAndCreatedBetween(long stationId, Instant start, Instant finish);
}
