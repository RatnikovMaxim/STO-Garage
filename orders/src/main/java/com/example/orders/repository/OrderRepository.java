package com.example.orders.repository;

import com.example.orders.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByStationIdAndCreatedBetween(long stationId, Instant start, Instant finish);
}
