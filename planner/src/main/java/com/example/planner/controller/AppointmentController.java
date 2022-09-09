package com.example.planner.controller;

import com.example.planner.dto.AppointmentRequestDTO;
import com.example.planner.dto.AppointmentResponseDTO;
import com.example.planner.dto.AppointmentServiceRequestDTO;
import com.example.planner.exception.*;
import com.example.planner.manager.AppointmentManager;
import com.example.planner.security.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/planner")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentManager manager;

    @GetMapping
    public List<AppointmentResponseDTO> getAll(
            @RequestAttribute final Authentication authentication,
            @RequestParam final long start, final long finish
    ) throws ForbiddenException {
        final List<AppointmentResponseDTO> responseDTO = manager.getAll(authentication, start, finish);
        return responseDTO;
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO getById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) throws ForbiddenException, InvalidStationException, InvalidUserException, AppointmentNotFoundException {
        final AppointmentResponseDTO responseDTO = manager.getById(authentication, id);
        return responseDTO;
    }

    @PostMapping
    public AppointmentResponseDTO create(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final AppointmentRequestDTO requestDTO
    ) throws ForbiddenException, InvalidStationException, TimeAlreadyTakenException {
        final AppointmentResponseDTO responseDTO = manager.create(authentication, requestDTO);
        return responseDTO;
    }

    @PutMapping
    public AppointmentResponseDTO update(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final AppointmentRequestDTO requestDTO
    ) throws ForbiddenException, InvalidStationException, InvalidUserException, TimeAlreadyTakenException {
        final AppointmentResponseDTO responseDTO = manager.update(authentication, requestDTO);
        return responseDTO;
    }

    @PostMapping("/{id}/services")
    public AppointmentResponseDTO addService(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id,
            @Valid @RequestBody final AppointmentServiceRequestDTO requestDTO
    ) throws ForbiddenException, InvalidStationException, InvalidUserException {
        return manager.addServiceForId(authentication, id, requestDTO);
    }

    @DeleteMapping("/{id}/services/{serviceId}")
    public AppointmentResponseDTO removeService(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id,
            @Min(1) @PathVariable final long serviceId
    ) throws ForbiddenException, InvalidStationException, InvalidUserException {
        return manager.removeServiceForId(authentication, id, serviceId);
    }

    @PutMapping("/{id}")
    public AppointmentResponseDTO finishById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) throws ForbiddenException, InvalidStationException {
        final AppointmentResponseDTO responseDTO = manager.finishById(authentication, id);
        return responseDTO;
    }

    @DeleteMapping("/{id}")
    public AppointmentResponseDTO removeById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) throws ForbiddenException, InvalidStationException, InvalidUserException {
        final AppointmentResponseDTO responseDTO = manager.removeById(authentication, id);
        return responseDTO;
    }
}
