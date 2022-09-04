package com.example.planner.manager;

import com.example.planner.client.CatalogServiceClient;
import com.example.planner.dto.AppointmentRequestDTO;
import com.example.planner.dto.AppointmentResponseDTO;
import com.example.planner.dto.AppointmentServiceRequestDTO;
import com.example.planner.dto.StationResponseDTO;
import com.example.planner.entity.AppointmentEntity;
import com.example.planner.entity.AppointmentServiceEntity;
import com.example.planner.exception.AppointmentNotFoundException;
import com.example.planner.exception.ForbiddenException;
import com.example.planner.repository.AppointmentRepository;
import com.example.planner.repository.AppointmentServiceRepository;
import com.example.planner.security.Authentication;
import com.example.planner.security.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.planner.security.Roles.*;

@Component
@Transactional
public class AppointmentManager {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final CatalogServiceClient catalogServiceClient;
    private final String appToken;

    public AppointmentManager(
            final AppointmentRepository appointmentRepository,
            final AppointmentServiceRepository appointmentServiceRepository,
            final CatalogServiceClient catalogServiceClient,
            @Value("${app.token}") final String appToken) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentServiceRepository = appointmentServiceRepository;
        this.catalogServiceClient = catalogServiceClient;
        this.appToken = appToken;
    }

    private final Function<AppointmentEntity.UserEmbedded, AppointmentResponseDTO.User> userEmbeddedToUser = embedded -> new AppointmentResponseDTO.User(
            embedded.getId(),
            embedded.getName()
    );
    private final Function<AppointmentEntity.StationEmbedded, AppointmentResponseDTO.Station> stationEmbeddedToStation = embedded -> new AppointmentResponseDTO.Station(
            embedded.getId(),
            embedded.getName()
    );
    private final Function<AppointmentServiceEntity.ServiceEmbedded, AppointmentResponseDTO.AppointmentService.Service> serviceEmbeddedToService = embedded -> new AppointmentResponseDTO.AppointmentService.Service(
            embedded.getId(),
            embedded.getName()
    );
    private final Function<AppointmentServiceEntity, AppointmentResponseDTO.AppointmentService> appointmentServiceEntityToAppointmentService = entity -> new AppointmentResponseDTO.AppointmentService(
            entity.getId(),
            serviceEmbeddedToService.apply(entity.getService())
    );
    private final Function<AppointmentEntity, AppointmentResponseDTO> appointmentEntityToAppointmentResponseDTO = entity -> new AppointmentResponseDTO(
            entity.getId(),
            userEmbeddedToUser.apply(entity.getUser()),
            stationEmbeddedToStation.apply(entity.getStation()),
            entity.getServices().stream().map(appointmentServiceEntityToAppointmentService).collect(Collectors.toList()),
            entity.getStatus()
    );
    private final Function<AppointmentRequestDTO.User, AppointmentEntity.UserEmbedded> userToUserEmbedded = dto -> new AppointmentEntity.UserEmbedded(
            dto.getId(),
            dto.getName()
    );
    private final Function<AppointmentRequestDTO.Station, AppointmentEntity.StationEmbedded> stationToStationEmbedded = dto -> new AppointmentEntity.StationEmbedded(
            dto.getId(),
            dto.getName()
    );

    public List<AppointmentResponseDTO> getAll(
            final Authentication authentication,
            final long start, final long finish) {

        if (authentication.hasRole(ROLE_PLANNER)) {

            return appointmentRepository.findAllByStationIdAndTimeBetween(
                            authentication.getStationId(), Instant.ofEpochSecond(start), Instant.ofEpochSecond(finish)
                    ).stream()
                    .map(appointmentEntityToAppointmentResponseDTO)
                    .collect(Collectors.toList());
        }

        if (authentication.hasRole(ROLE_USER)) {

            return appointmentRepository.findAllByUserIdAndTimeBetween(
                            authentication.getId(), Instant.ofEpochSecond(start), Instant.ofEpochSecond(finish)
                    ).stream()
                    .map(appointmentEntityToAppointmentResponseDTO)
                    .collect(Collectors.toList());
        }

        else throw new ForbiddenException();
    }

    public AppointmentResponseDTO getById(final Authentication authentication, long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_PLANNER)) {
            throw new ForbiddenException();
        }
        return appointmentRepository.findById(id)
                .map(appointmentEntityToAppointmentResponseDTO)
                .orElseThrow(AppointmentNotFoundException::new)
                ;
    }

    public AppointmentResponseDTO create(final Authentication authentication, final AppointmentRequestDTO requestDTO) {
        if (!authentication.hasRole(ROLE_USER)) {
            throw new ForbiddenException();
        }

        final AppointmentEntity appointmentEntity = new AppointmentEntity(
                0,
                userToUserEmbedded.apply(requestDTO.getUser()),
                stationToStationEmbedded.apply(requestDTO.getStation()),
                Collections.emptyList(),
                requestDTO.getTime(),
                requestDTO.getStatus(),
                Instant.now()
        );
        final AppointmentEntity savedEntity = appointmentRepository.save(appointmentEntity);
        return appointmentEntityToAppointmentResponseDTO.apply(savedEntity);
    }

    public AppointmentResponseDTO addServiceForId(final Authentication authentication, final long id, final AppointmentServiceRequestDTO requestDTO) {
        AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);
        final StationResponseDTO stationDTO = catalogServiceClient.getStationById(appToken, appointmentEntity.getStation().getId());
        final StationResponseDTO.Service service = stationDTO.getServices().stream()
                .filter(o -> o.getId() == requestDTO.getServiceId())
                .findFirst()
                .orElseThrow(RuntimeException::new);

        AppointmentServiceEntity appointmentServiceEntity = new AppointmentServiceEntity(
                0,
                new AppointmentServiceEntity.ServiceEmbedded(service.getId(), service.getName()),
                appointmentEntity
        );
        AppointmentServiceEntity savedEntity = appointmentServiceRepository.save(appointmentServiceEntity);
        return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
    }

    public AppointmentResponseDTO removeServiceForId(final Authentication authentication, final long id, final long positionId) {
        AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);
        AppointmentServiceEntity appointmentServiceEntity = appointmentServiceRepository.findById(positionId).orElseThrow(RuntimeException::new);
        if (appointmentServiceEntity.getService().getId() != appointmentEntity.getId()) {
        }

        appointmentServiceRepository.delete(appointmentServiceEntity);
        appointmentEntity.getServices().remove(appointmentServiceEntity);

        return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
    }

    public AppointmentResponseDTO finishById(final Authentication authentication, final long id) {

        final AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);
        appointmentEntity.setStatus("клиент приехал");
        return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
    }

    public void deleteById(Authentication authentication, long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN) && authentication.hasRole(ROLE_PLANNER)) {
            throw new ForbiddenException();
        }
        appointmentRepository.deleteById(id);
    }
}