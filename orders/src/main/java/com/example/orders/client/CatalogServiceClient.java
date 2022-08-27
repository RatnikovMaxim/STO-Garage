package com.example.orders.client;

import com.example.orders.dto.StationResponseDTO;
import com.example.orders.dto.VerificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "catalog")
public interface CatalogServiceClient {
    @GetMapping("/stations/{id}")
    StationResponseDTO getStationById(
            @RequestHeader("X-Token") String serviceToken,
            @PathVariable long id
    );
}