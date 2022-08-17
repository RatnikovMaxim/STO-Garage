package org.example.manager;

import com.example.id.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.example.controller.CatalogStoController;
import org.example.dto.CatalogStoResponseDTO;
import org.example.entity.CatalogStoEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class CatalogStoManager {
    private final CatalogStoController catalogStoRepository;
    private final UserRepository userRepository;


    private final Function<CatalogStoEntity, CatalogStoResponseDTO> catalogStoEntityToCatalogStoResponseDTO = catalogStoEntity -> new CatalogStoResponseDTO (
            catalogStoEntity.getId(),
            catalogStoEntity.getName(),
            catalogStoEntity.getAddress(),
            catalogStoEntity.getOperating_mode(),
            catalogStoEntity.getServices(),
            catalogStoEntity.getTelephone(),
            catalogStoEntity.getPhoto()
    );


    public List<CatalogStoResponseDTO> getAll() {

        return catalogStoRepository.findAll().stream()
                .map(catalogStoEntityToCatalogStoResponseDTO)
                .collect(Collectors.toList());

    }

}
