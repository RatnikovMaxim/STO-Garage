package org.example.repository;

import org.example.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
}
