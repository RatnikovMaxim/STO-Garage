package org.example.controller;


import org.example.dto.CatalogResponseDTO;

import org.example.security.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @GetMapping
    public List<CatalogResponseDTO> getAll(
            @RequestAttribute final Authentication authentication
    ) {
        return Collections.emptyList();
    }
}
