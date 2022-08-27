package com.example.catalog.repository;

import com.example.catalog.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StationRepository extends JpaRepository<StationEntity, Long> {
    @Query(value = "SELECT * From stations\n" +
            "                WHERE removed = FALSE\n" +
            "                AND to_tsvector(CAST(:language  AS regconfig), name || ' ') @@ to_tsquery(CAST(:language AS regconfig), :query)\n" +
            "                ORDER BY id", nativeQuery = true)
    List<StationEntity> search(String query, String language);
}
