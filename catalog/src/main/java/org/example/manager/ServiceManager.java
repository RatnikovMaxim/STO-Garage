package org.example.manager;

import com.example.id.security.Authentication;
import com.example.id.security.Roles;
import lombok.RequiredArgsConstructor;
import org.example.dto.ServiceRequestDTO;
import org.example.dto.ServiceResponseDTO;
import org.example.entity.ServiceEntity;
import org.example.entity.StationEntity;
import org.example.exception.CatalogStoNotFoundException;
import org.example.exception.ForbiddenException;
import org.example.repository.ServiceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class ServiceManager {
    private final ServiceRepository serviceRepository;


    private final Function<ServiceEntity, ServiceResponseDTO> serviceEntityServiceResponseDTOFunction = entity -> new ServiceResponseDTO(
            entity.getId(),
            entity.getName()
    );

    public List<ServiceResponseDTO> getAll() {

        return serviceRepository.findAll().stream()
                .map(serviceEntityServiceResponseDTOFunction)
                .collect(Collectors.toList());
    }

    public ServiceResponseDTO getById(Authentication authentication, long id) {
        return serviceRepository.findById(id)
                .map(serviceEntityServiceResponseDTOFunction)
                .orElseThrow(CatalogStoNotFoundException::new)
                ;
    }

    public ServiceResponseDTO create(final Authentication authentication, final ServiceRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        final ServiceEntity serviceEntity = new ServiceEntity(
                0,
                requestDTO.getName()
        );

        final ServiceEntity savedEntity = serviceRepository.save(serviceEntity);
        return serviceEntityServiceResponseDTOFunction.apply(savedEntity);
    }

    public ServiceResponseDTO update(final Authentication authentication, final ServiceRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        final ServiceEntity serviceEntity = serviceRepository.getReferenceById(requestDTO.getId());
        serviceEntity.setName(requestDTO.getName());
        return serviceEntityServiceResponseDTOFunction.apply(serviceEntity);
    }

    public void deleteById(final Authentication authentication, final long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        serviceRepository.deleteById(id);
    }
}
