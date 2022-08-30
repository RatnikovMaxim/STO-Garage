package com.example.planner.client;

import com.example.planner.dto.StationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "catalog")
public interface CatalogServiceClient {
    @GetMapping("/stations/{id}")
    StationResponseDTO getStationById(
            @RequestHeader("X-Token") String serviceToken,
            @PathVariable long id
    );
}