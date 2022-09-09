package com.example.catalog.controller;


import com.example.catalog.dto.InvalidRequestExceptionDTO;
import com.example.catalog.dto.StationSearchResponseDTO;
import com.example.catalog.manager.StationSearchManager;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/stations_search")
@RequiredArgsConstructor
public class StationSearchController {
    private final StationSearchManager manager;

    @GetMapping
    public List<StationSearchResponseDTO> search(String query, String language) throws InvalidRequestExceptionDTO {
        return manager.search(query, language);

    }
}
