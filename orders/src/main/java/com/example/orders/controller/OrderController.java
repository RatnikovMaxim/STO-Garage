package com.example.orders.controller;

import com.example.id.security.Authentication;
import com.example.orders.dto.OrderPositionRequestDTO;
import com.example.orders.dto.OrderRequestDTO;
import com.example.orders.dto.OrderResponseDTO;
import com.example.orders.manager.OrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderManager manager;

    @GetMapping // /orders?stationId=...&start=...&finish=...
    public List<OrderResponseDTO> getAll(
            @RequestAttribute final Authentication authentication,
            @RequestParam final long stationId, final long start, final long finish
    ) {
        final List<OrderResponseDTO> responseDTO = manager.getAll(authentication, stationId, start, finish);
        return responseDTO;
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        final OrderResponseDTO responseDTO = manager.getById(authentication, id);
        return responseDTO;
    }

    @PostMapping
    public OrderResponseDTO create(
            @RequestAttribute final Authentication authentication,
            @Valid @RequestBody final OrderRequestDTO requestDTO
    ) {
        final OrderResponseDTO responseDTO = manager.create(authentication, requestDTO);
        return responseDTO;
    }

    @PostMapping("/{id}/positions")
    public OrderResponseDTO addPosition(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id,
            @Valid @RequestBody final OrderPositionRequestDTO requestDTO
    ) {
        return manager.addPositionForId(authentication, id, requestDTO);
    }

    @DeleteMapping("/{id}/positions/{positionId}")
    public OrderResponseDTO addPosition(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id,
            @Min(1) @PathVariable final long positionId
    ) {
        return manager.removePositionForId(authentication, id, positionId);
    }

    @PutMapping("/{id}")
    public OrderResponseDTO finishById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        final OrderResponseDTO responseDTO = manager.finishById(authentication, id);
        return responseDTO;
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @RequestAttribute final Authentication authentication,
            @Min(1) @PathVariable final long id
    ) {
        manager.deleteById(authentication, id);
    }

}
