package org.example.repository;

import org.example.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    Optional<ServiceEntity> findById(Long id);

    List<ServiceEntity> findAllByIdIn(List<Long> serviceIds);

}
