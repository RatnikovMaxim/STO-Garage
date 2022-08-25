package com.example.catalog.repository;

import com.example.catalog.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
