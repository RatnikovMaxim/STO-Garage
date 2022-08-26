package com.example.catalog.controller;

import com.example.catalog.dto.StationRequestDTO;
import com.example.catalog.dto.StationResponseDTO;
import com.example.catalog.manager.StationManager;
import com.example.id.security.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationManager manager;

    @GetMapping
    public List<StationResponseDTO> getAll() {
        final List<StationResponseDTO> responseDTO = manager.getAll();
        return responseDTO;
    }
    @GetMapping("/{id}")
    public StationResponseDTO getById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        final StationResponseDTO responseDTO = manager.getById(authentication, id);
        return responseDTO;
    }

    @PostMapping
    public StationResponseDTO create(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final StationRequestDTO requestDTO
    ) {
        final StationResponseDTO responseDTO = manager.create(authentication, requestDTO);
        return responseDTO;
    }
    @PutMapping
    public StationResponseDTO update(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final StationRequestDTO requestDTO
    ) {
        final StationResponseDTO responseDTO = manager.update(authentication, requestDTO);
        return responseDTO;
    }
    @DeleteMapping("/{id}")
    public void deleteById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        manager.deleteById(authentication, id);
    }

}
