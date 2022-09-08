package com.example.catalog.manager;

import com.example.id.security.Authentication;
import com.example.id.security.Roles;
import lombok.RequiredArgsConstructor;
import com.example.catalog.dto.StationRequestDTO;
import com.example.catalog.dto.StationResponseDTO;
import com.example.catalog.entity.ServiceEntity;
import com.example.catalog.entity.StationEntity;
import com.example.catalog.exception.ForbiddenException;
import com.example.catalog.exception.StationNotFoundException;
import com.example.catalog.repository.ServiceRepository;
import com.example.catalog.repository.StationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.id.security.Roles.ROLE_CATALOG;

@Component
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StationManager {
    private final StationRepository stationRepository;
    private final ServiceRepository serviceRepository;

    private final Function<StationEntity, StationResponseDTO> stationEntityStationResponseDTOFunction = entity -> new StationResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getServices().stream().map(o -> new StationResponseDTO.Service(
                    o.getId(),
                    o.getName())).collect(Collectors.toList())

    );

    public List<StationResponseDTO> getAll() {
        log.info("Получение списка CTO");

        return stationRepository.findAll().stream()
                .map(stationEntityStationResponseDTOFunction)
                .collect(Collectors.toList());
    }

    public StationResponseDTO getById(Authentication authentication, long id) {
        log.info("Получение CTO по ID");
        return stationRepository.findById(id)
                .map(stationEntityStationResponseDTOFunction)
                .orElseThrow(StationNotFoundException::new);
    }

    public StationResponseDTO create(final Authentication authentication, final StationRequestDTO requestDTO) {
        log.info("Создание СТО в каталог");
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_CATALOG)){
            throw new ForbiddenException();
        }

        final List<ServiceEntity> servicesEntities = serviceRepository.findAllByIdIn(requestDTO.getServiceIds());

        final StationEntity stationEntity = new StationEntity(
                0,
                requestDTO.getName(),
                servicesEntities
        );

        final StationEntity savedEntity = stationRepository.save(stationEntity);
        return stationEntityStationResponseDTOFunction.apply(savedEntity);
    }

    public StationResponseDTO update(final Authentication authentication, final StationRequestDTO requestDTO) {
        log.info("Изменение CTO");
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_CATALOG)) {
            throw new ForbiddenException();
        }

        if (!(authentication.getStationId() == requestDTO.getId())){
            throw new ForbiddenException();
        }

        final List<ServiceEntity> servicesEntities = serviceRepository.findAllByIdIn(requestDTO.getServiceIds());

        final StationEntity stationEntity = stationRepository.getReferenceById(requestDTO.getId());
        stationEntity.setName(requestDTO.getName());
        stationEntity.setServices(servicesEntities);

        return stationEntityStationResponseDTOFunction.apply(stationEntity);
    }

    public void deleteById(final Authentication authentication, final long id) {
        log.info("Удаление СТО окончательно");
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_CATALOG)) {
            throw new ForbiddenException();
        }
        stationRepository.deleteById(id);
    }

}

