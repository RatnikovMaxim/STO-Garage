package org.example.manager;

import com.example.id.security.Authentication;
import com.example.id.security.Roles;
import lombok.RequiredArgsConstructor;
import org.example.dto.StationRequestDTO;
import org.example.dto.StationResponseDTO;
import org.example.entity.ServiceEntity;
import org.example.entity.StationEntity;
import org.example.exception.ServiceNotFoundException;
import org.example.exception.ForbiddenException;
import org.example.exception.StationNotFoundException;
import org.example.repository.ServiceRepository;
import org.example.repository.StationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.id.security.Roles.ROLE_SERVICE;

@Component
@Transactional
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

        return stationRepository.findAll().stream()
                .map(stationEntityStationResponseDTOFunction)
                .collect(Collectors.toList());
    }

    public StationResponseDTO getById(Authentication authentication, long id) {
        return stationRepository.findById(id)
                .map(stationEntityStationResponseDTOFunction)
                .orElseThrow(StationNotFoundException::new)
                ;
    }

    public StationResponseDTO create(final Authentication authentication, final StationRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)&&authentication.hasRole(ROLE_SERVICE)) {
            throw new ForbiddenException();
        }

        final List<ServiceEntity> servicesEntities = serviceRepository.findAllById(requestDTO.getServiceIds());

        final StationEntity stationEntity = new StationEntity(
                0,
                requestDTO.getName(),
                servicesEntities
        );

        final StationEntity savedEntity = stationRepository.save(stationEntity);
        return stationEntityStationResponseDTOFunction.apply(savedEntity);
    }

    public StationResponseDTO update(final Authentication authentication, final StationRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)&&authentication.hasRole(ROLE_SERVICE)) {
            throw new ForbiddenException();
        }

        final List<ServiceEntity> servicesEntities = serviceRepository.findAllById(requestDTO.getServiceIds());

        final StationEntity stationEntity = stationRepository.getReferenceById(requestDTO.getId());
        stationEntity.setName(requestDTO.getName());
        stationEntity.setServices(servicesEntities);

        return stationEntityStationResponseDTOFunction.apply(stationEntity);
    }

    public void deleteById(final Authentication authentication, final long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)&&authentication.hasRole(ROLE_SERVICE)) {
            throw new ForbiddenException();
        }
        stationRepository.deleteById(id);
    }
}

