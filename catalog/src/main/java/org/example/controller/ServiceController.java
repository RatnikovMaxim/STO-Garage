package org.example.controller;


import com.example.id.security.Authentication;
import lombok.RequiredArgsConstructor;
import org.example.dto.ServiceRequestDTO;
import org.example.dto.ServiceResponseDTO;
import org.example.manager.ServiceManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceManager manager;

    @GetMapping
    public List<ServiceResponseDTO> getAll() {
        final List<ServiceResponseDTO> responseDTO = manager.getAll();
        return responseDTO;
    }
    @GetMapping("/{id}")
    public ServiceResponseDTO getById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        final ServiceResponseDTO responseDTO = manager.getById(authentication, id);
        return responseDTO;
    }

    @PostMapping
    public ServiceResponseDTO create(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final ServiceRequestDTO requestDTO
    ) {
        final ServiceResponseDTO responseDTO = manager.create(authentication, requestDTO);
        return responseDTO;
    }
    @PutMapping
    public ServiceResponseDTO update(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final ServiceRequestDTO requestDTO
    ) {
        final ServiceResponseDTO responseDTO = manager.update(authentication, requestDTO);
        return responseDTO;
    }
    @DeleteMapping
    public void deleteById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        manager.deleteById(authentication, id);
    }

}

