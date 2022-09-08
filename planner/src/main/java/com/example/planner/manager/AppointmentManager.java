package com.example.planner.manager;

import com.example.planner.client.CatalogServiceClient;
import com.example.planner.dto.*;
import com.example.planner.entity.AppointmentEntity;
import com.example.planner.entity.AppointmentServiceEntity;
import com.example.planner.exception.*;
import com.example.planner.repository.AppointmentRepository;
import com.example.planner.repository.AppointmentServiceRepository;
import com.example.planner.security.Authentication;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            final long start, final long finish) throws ForbiddenException {

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

        else throw new ForbiddenException(); // если ты не USER и не PLANNER
    }

    public AppointmentResponseDTO getById(final Authentication authentication, long id) throws InvalidStationException, InvalidUserException, AppointmentNotFoundException, ForbiddenException {
        final AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);

        if (authentication.hasRole(ROLE_PLANNER)) {
            if (appointmentEntity.getStation().getId() != authentication.getStationId()) {
                throw new InvalidStationException(); // если СТО не та на которой ты работаешь
            }
                return appointmentRepository.findById(id)
                        .map(appointmentEntityToAppointmentResponseDTO)
                        .orElseThrow(AppointmentNotFoundException::new)
                        ;
        }

        if (authentication.hasRole(ROLE_USER)) {
            if (appointmentEntity.getUser().getId() != authentication.getId()) {
                throw new InvalidUserException(); // если это не твой ремонт
            }
                return appointmentRepository.findById(id)
                        .map(appointmentEntityToAppointmentResponseDTO)
                        .orElseThrow(AppointmentNotFoundException::new)
                        ;
        }

    else throw new ForbiddenException(); // если ты не USER и не PLANNER
    }

    public AppointmentResponseDTO create(final Authentication authentication, final AppointmentRequestDTO requestDTO) throws InvalidStationException, ForbiddenException, TimeAlreadyTakenException {

        if (authentication.hasRole(ROLE_USER)) {
            return createAppointment(requestDTO);
        }

        if (authentication.hasRole(ROLE_PLANNER)) {
            if (requestDTO.getStation().getId() != authentication.getStationId()) {
                throw new InvalidStationException(); // если СТО не та на которой ты работаешь
            }
            return createAppointment(requestDTO);
        }

        else throw new ForbiddenException(); // если ты не USER и не PLANNER
    }

    private AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO) throws TimeAlreadyTakenException {
        if (appointmentRepository.findAllByStationIdAndTime(requestDTO.getStation().getId(), requestDTO.getTime()) != null) {
            throw new TimeAlreadyTakenException(); // если время на этой СТО занято
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

    public AppointmentResponseDTO update(final Authentication authentication, final AppointmentRequestDTO requestDTO) throws TimeAlreadyTakenException, InvalidUserException, InvalidStationException, ForbiddenException {
        final AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(requestDTO.getId());

        if (appointmentRepository.findAllByStationIdAndTime(requestDTO.getStation().getId(), requestDTO.getTime()) != null) {
            throw new TimeAlreadyTakenException(); // если время на этой СТО занято
        }

        if (authentication.hasRole(ROLE_USER)) {
            if (appointmentEntity.getUser().getId() != authentication.getId()) {
                throw new InvalidUserException(); // если запрос на не твой плановый ремонт
            }
            appointmentEntity.setTime(requestDTO.getTime());
            return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
        }

        if (authentication.hasRole(ROLE_PLANNER)) {
            if (appointmentEntity.getStation().getId() != authentication.getStationId()) {
                throw new InvalidStationException(); // если СТО не та на которой ты работаешь
            }
                    appointmentEntity.setTime(requestDTO.getTime());
                    return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
        }

        else throw new ForbiddenException(); // если ты не USER и не PLANNER
    }

    public AppointmentResponseDTO addServiceForId(final Authentication authentication, final long id, final AppointmentServiceRequestDTO requestDTO) throws InvalidUserException, InvalidStationException, ForbiddenException {
        AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);

        if (authentication.hasRole(ROLE_USER)) {
            if (appointmentEntity.getUser().getId() != authentication.getId()) {
                throw new InvalidUserException(); // если запрос на не твой плановый ремонт
            }
            return addServiceForIdAppointment(requestDTO, appointmentEntity);
        }

        if (authentication.hasRole(ROLE_PLANNER)) {
            if (appointmentEntity.getStation().getId() != authentication.getStationId()) {
                throw new InvalidStationException(); // если СТО не та на которой ты работаешь
            }
            return addServiceForIdAppointment(requestDTO, appointmentEntity);
        }

        else throw new ForbiddenException(); // если ты не USER и не PLANNER
    }

    private AppointmentResponseDTO addServiceForIdAppointment(AppointmentServiceRequestDTO requestDTO, AppointmentEntity appointmentEntity) {
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

    public AppointmentResponseDTO removeServiceForId(final Authentication authentication, final long id, final long positionId) throws InvalidUserException, InvalidStationException, ForbiddenException {
        AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);

        if (authentication.hasRole(ROLE_USER)) {
            if (appointmentEntity.getUser().getId() != authentication.getId()) {
                throw new InvalidUserException(); // если запрос на не твой плановый ремонт
            }
            return removeServiceForIdAppointment(positionId, appointmentEntity);
        }

        if (authentication.hasRole(ROLE_PLANNER)) {
            if (appointmentEntity.getStation().getId() != authentication.getStationId()) {
                throw new InvalidStationException(); // если СТО не та на которой ты работаешь
            }
            return removeServiceForIdAppointment(positionId, appointmentEntity);
        }

        else throw new ForbiddenException(); // если ты не USER и не PLANNER
    }

    private AppointmentResponseDTO removeServiceForIdAppointment(long positionId, AppointmentEntity appointmentEntity) throws ForbiddenException {
        AppointmentServiceEntity appointmentServiceEntity = appointmentServiceRepository.findById(positionId).orElseThrow(RuntimeException::new);
        if (appointmentServiceEntity.getAppointment().getId() != appointmentEntity.getId()) {
            throw new ForbiddenException();
        }
        appointmentServiceRepository.delete(appointmentServiceEntity);
        appointmentEntity.getServices().remove(appointmentServiceEntity);

        return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
    }

    public AppointmentResponseDTO finishById(final Authentication authentication, final long id) throws InvalidStationException, ForbiddenException {

        if (authentication.hasRole(ROLE_PLANNER)) {
            final AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);

            if (appointmentEntity.getStation().getId() != authentication.getStationId()) {
                throw new InvalidStationException(); // если СТО не та на которой ты работаешь
            }
                appointmentEntity.setStatus("клиент приехал");
                return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
        }

        else throw new ForbiddenException(); //  если ты не PLANNER
    }

    public AppointmentResponseDTO removeById(Authentication authentication, long id) throws InvalidStationException, InvalidUserException, ForbiddenException {

        if (authentication.hasRole(ROLE_PLANNER)) {
            final AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);

            if (appointmentEntity.getStation().getId() != authentication.getStationId()) {
                throw new InvalidStationException(); // если СТО не та на которой ты работаешь
            }
                appointmentEntity.setStatus("отменено");
                return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
        }

        if (authentication.hasRole(ROLE_USER)) {
            final AppointmentEntity appointmentEntity = appointmentRepository.getReferenceById(id);

            if (appointmentEntity.getUser().getId() != authentication.getId()) {
                throw new InvalidUserException(); // если запрос на не твой плановый ремонт
            }
                appointmentEntity.setStatus("отменено");
                return appointmentEntityToAppointmentResponseDTO.apply(appointmentEntity);
        }

        else throw new ForbiddenException(); // если ты не USER и не PLANNER
    }
}