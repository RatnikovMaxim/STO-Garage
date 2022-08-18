package org.example.controller;

import com.example.id.security.Authentication;
import lombok.RequiredArgsConstructor;
import org.example.dto.CatalogStoResponseDTO;
import org.example.manager.CatalogStoManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/catalogs_sto")
@RequiredArgsConstructor
public class CatalogStoController {
    private final CatalogStoManager manager;

    @GetMapping
    public List<CatalogStoResponseDTO> getAll() {
        final List<CatalogStoResponseDTO> responseDTO = manager.getAll();
        return responseDTO;
    }

}
