package com.example.planner.repository;

import com.example.planner.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findAllByStationIdAndTimeBetween(long stationId, Instant start, Instant finish);
    List<AppointmentEntity> findAllByUserIdAndTimeBetween(long userId, Instant start, Instant finish);
    AppointmentEntity findAllByStationIdAndTime(long stationId, Instant time);
}
