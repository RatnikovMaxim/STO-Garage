package org.example.repository;

import org.example.entity.CatalogStoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogStoRepository extends JpaRepository<CatalogStoEntity, Long> {

}
