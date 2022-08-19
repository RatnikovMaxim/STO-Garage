package org.example.controller;

import com.example.id.security.Authentication;
import lombok.RequiredArgsConstructor;
import org.example.dto.CatalogStoRequestDTO;
import org.example.dto.CatalogStoResponseDTO;
import org.example.manager.CatalogStoManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    @GetMapping
    public CatalogStoResponseDTO getById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        final CatalogStoResponseDTO responseDTO = manager.getById(authentication, id);
        return responseDTO;
    }
    /*
    @PostMapping
    public CatalogStoResponseDTO create(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final CatalogStoRequestDTO requestDTO
    ) {
        final CatalogStoResponseDTO responseDTO = manager.create(authentication, requestDTO);
        return responseDTO;
    }
    */

    @PutMapping
    public CatalogStoResponseDTO update(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final CatalogStoRequestDTO requestDTO
    ) {
        final CatalogStoResponseDTO responseDTO = manager.update(authentication, requestDTO);
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
