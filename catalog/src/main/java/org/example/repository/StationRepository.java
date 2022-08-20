package org.example.repository;

import org.example.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
