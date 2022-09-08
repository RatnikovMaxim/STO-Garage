package com.example.catalog.manager;

import com.example.id.security.Authentication;
import com.example.id.security.Roles;
import lombok.RequiredArgsConstructor;
import com.example.catalog.dto.ServiceRequestDTO;
import com.example.catalog.dto.ServiceResponseDTO;
import com.example.catalog.entity.ServiceEntity;
import com.example.catalog.exception.ServiceNotFoundException;
import com.example.catalog.exception.ForbiddenException;
import com.example.catalog.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.id.security.Roles.ROLE_CATALOG;


@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ServiceManager {
    private final ServiceRepository serviceRepository;

    private final Function<ServiceEntity, ServiceResponseDTO> serviceEntityServiceResponseDTOFunction = entity -> new ServiceResponseDTO(
            entity.getId(),
            entity.getName()
    );

    public List<ServiceResponseDTO> getAll() {
        log.info("Получение списка услуг СТО");

        return serviceRepository.findAll().stream()
                .map(serviceEntityServiceResponseDTOFunction)
                .collect(Collectors.toList());
    }

    public ServiceResponseDTO getById(Authentication authentication, long id) {
        log.info("Получение из списка услуги CTO по ID");
        return serviceRepository.findById(id)
                .map(serviceEntityServiceResponseDTOFunction)
                .orElseThrow(ServiceNotFoundException::new)
                ;
    }

    public ServiceResponseDTO create(final Authentication authentication, final ServiceRequestDTO requestDTO) {
        log.info("Создание услуги в СТО каталога");
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_CATALOG)) {
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
        log.info("Изменение услуги CTO");
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_CATALOG)) {
            throw new ForbiddenException();
        }
        if (!(authentication.getStationId() == requestDTO.getId())){
            throw new ForbiddenException();
        }
        final ServiceEntity serviceEntity = serviceRepository.getReferenceById(requestDTO.getId());
        serviceEntity.setName(requestDTO.getName());
        return serviceEntityServiceResponseDTOFunction.apply(serviceEntity);
    }

    public void deleteById(final Authentication authentication, final long id) {
        log.info("Удаление услуги СТО окончательно");
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_CATALOG)) {
            throw new ForbiddenException();
        }
        serviceRepository.deleteById(id);
    }
}
