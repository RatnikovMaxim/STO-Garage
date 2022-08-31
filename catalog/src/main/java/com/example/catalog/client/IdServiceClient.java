package com.example.catalog.client;

import com.example.id.dto.VerificationRequestDTO;
//import org.example.dto.VerificationRequestDTO;
import com.example.catalog.dto.VerificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "id")
public interface IdServiceClient {
    @PostMapping("/auth/verify")
    VerificationResponseDTO verify(
            @RequestHeader("X-Token") String serviceToken,
            @RequestBody VerificationRequestDTO requestDTO
    );
}