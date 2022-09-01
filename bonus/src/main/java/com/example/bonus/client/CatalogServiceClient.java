package com.example.bonus.client;

import com.example.bonus.dto.StationResponseDTO;
import com.example.bonus.dto.VerificationResponseDTO;
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
