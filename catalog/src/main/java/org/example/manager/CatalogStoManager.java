package org.example.manager;

import com.example.id.entity.UserEntity;
import com.example.id.repository.UserRepository;
import com.example.id.security.Authentication;
import com.example.id.security.Roles;
import lombok.RequiredArgsConstructor;
import org.example.controller.CatalogStoController;
import org.example.dto.CatalogStoRequestDTO;
import org.example.dto.CatalogStoResponseDTO;
import org.example.entity.CatalogStoEntity;
import org.example.exception.CatalogStoNotFoundException;
import org.example.exception.ForbiddenException;
import org.example.repository.CatalogStoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class CatalogStoManager {
    private final CatalogStoRepository catalogStoRepository;
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
    public CatalogStoResponseDTO getById(Authentication authentication, long id) {
        return catalogStoRepository.findById(id)
                .map(catalogStoEntityToCatalogStoResponseDTO)
                .orElseThrow(CatalogStoNotFoundException::new)
                ;
    }
    public CatalogStoResponseDTO create(final Authentication authentication, final CatalogStoRequestDTO requestDTO) {
    if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
        throw new ForbiddenException();
    }

        final UserEntity userEntity = userRepository.getReferenceById(authentication.getId());

        final CatalogStoEntity catalogStoEntity = new CatalogStoEntity(
                0,
                userEntity,
                requestDTO.getName(),
                requestDTO.getAddress(),
                requestDTO.getOperating_mode(),
                requestDTO.getServices(),
                requestDTO.getTelephone(),
                requestDTO.getPhoto(),
               /* Optional.ofNullable(requestDTO. ...())
                        .map(o -> new ...Embeddable(o.get...(), o.get...()))
                        .orElse(null)*/
        );
        final CatalogStoEntity savedEntity = catalogStoRepository.save(catalogStoEntity);
        return catalogStoEntityToCatalogStoResponseDTO.apply(savedEntity);
    }
    public CatalogStoResponseDTO update(final Authentication authentication, final CatalogStoRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        final CatalogStoEntity catalogStoEntity = catalogStoRepository.getReferenceById(requestDTO.getId());
        catalogStoEntity.setName(requestDTO.getName());
        catalogStoEntity.setAddress(requestDTO.getAddress());
        catalogStoEntity.setOperating_mode(requestDTO.getOperating_mode());
        catalogStoEntity.setServices(requestDTO.getServices());
        catalogStoEntity.setTelephone(requestDTO.getTelephone());
        catalogStoEntity.setPhoto(requestDTO.getPhoto());


        return catalogStoEntityToCatalogStoResponseDTO.apply(catalogStoEntity);
    }

    public void deleteById(final Authentication authentication, final long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        catalogStoRepository.deleteById(id);
    }
}
