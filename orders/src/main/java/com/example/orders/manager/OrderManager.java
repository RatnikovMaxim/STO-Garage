package com.example.orders.manager;

import com.example.id.security.Roles;

import static com.example.id.security.Roles.ROLE_USER;

import com.example.orders.client.CatalogServiceClient;
import com.example.orders.client.IdServiceClient;
import com.example.orders.dto.OrderPositionRequestDTO;
import com.example.orders.dto.OrderRequestDTO;
import com.example.orders.dto.OrderResponseDTO;
import com.example.orders.dto.StationResponseDTO;
import com.example.orders.entity.OrderEntity;
import com.example.orders.entity.OrderPositionEntity;
import com.example.orders.exception.ForbiddenException;
import com.example.orders.exception.OrderNotFoundException;
import com.example.orders.exception.ServiceAlreadyExistsException;
import com.example.orders.model.OrderMessage;
import com.example.orders.repository.OrderPositionRepository;
import com.example.orders.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.id.security.Authentication;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@Slf4j
public class OrderManager {
    private final OrderRepository orderRepository;
    private final OrderPositionRepository orderPositionRepository;
    private final CatalogServiceClient catalogServiceClient;
    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;
    private final String appToken;

    public OrderManager(
            final OrderRepository orderRepository,
            final OrderPositionRepository orderPositionRepository,
            final CatalogServiceClient catalogServiceClient,
            final KafkaTemplate<String, OrderMessage> kafkaTemplate,
            @Value("${app.token}") final String appToken) {
        this.orderRepository = orderRepository;
        this.orderPositionRepository = orderPositionRepository;
        this.catalogServiceClient = catalogServiceClient;
        this.kafkaTemplate = kafkaTemplate;
        this.appToken = appToken;
    }

    private final Function<OrderEntity.UserEmbedded, OrderResponseDTO.User> userEmbeddedToUser = embedded -> new OrderResponseDTO.User(
            embedded.getId(),
            embedded.getName()
    );
    private final Function<OrderEntity.StationEmbedded, OrderResponseDTO.Station> stationEmbeddedToStation = embedded -> new OrderResponseDTO.Station(
            embedded.getId(),
            embedded.getName()
    );
    private final Function<OrderPositionEntity.ServiceEmbedded, OrderResponseDTO.OrderPosition.Service> serviceEmbeddedToService = embedded -> new OrderResponseDTO.OrderPosition.Service(
            embedded.getId(),
            embedded.getName()
    );
    private final Function<OrderPositionEntity, OrderResponseDTO.OrderPosition> orderPositionEntityToOrderPosition = entity -> new OrderResponseDTO.OrderPosition(
            entity.getId(),
            serviceEmbeddedToService.apply(entity.getService()),
            entity.getPrice()
    );
    private final Function<OrderEntity, OrderResponseDTO> orderEntityToOrderResponseDTO = entity -> new OrderResponseDTO(
            entity.getId(),
            userEmbeddedToUser.apply(entity.getUser()),
            stationEmbeddedToStation.apply(entity.getStation()),
            entity.getPositions().stream().map(orderPositionEntityToOrderPosition).collect(Collectors.toList()),
            entity.getStatus()
    );
    private final Function<OrderRequestDTO.User, OrderEntity.UserEmbedded> userToUserEmbedded = dto -> new OrderEntity.UserEmbedded(
            dto.getId(),
            dto.getName()
    );
    private final Function<OrderRequestDTO.Station, OrderEntity.StationEmbedded> stationToStationEmbedded = dto -> new OrderEntity.StationEmbedded(
            dto.getId(),
            dto.getName()
    );

    public List<OrderResponseDTO> getAll(
            final Authentication authentication,
            final long stationId, final long start, final long finish) {
        // TODO: check stationId: authentication.ROLE = ROLE_ORDER && authentication.stationId == stationId
        if (!authentication.hasRole(Roles.ROLE_ORDER) && authentication.hasRole(ROLE_USER)) {
            throw new ForbiddenException();
        }
        return orderRepository.findAllByStationIdAndCreatedBetween(
                        stationId, Instant.ofEpochSecond(start), Instant.ofEpochSecond(finish)
                ).stream()
                .map(orderEntityToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getById(final Authentication authentication, long id) {
        if (!authentication.hasRole(Roles.ROLE_ORDER) && authentication.hasRole(ROLE_USER)) {
            throw new ForbiddenException();
        }
        return orderRepository.findById(id)
                .map(orderEntityToOrderResponseDTO)
                .orElseThrow(OrderNotFoundException::new)
                ;
    }

    public OrderResponseDTO create(final Authentication authentication, final OrderRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ORDER)) {
            throw new ForbiddenException();
        }

        final OrderEntity orderEntity = new OrderEntity(
                0,
                userToUserEmbedded.apply(requestDTO.getUser()),
                stationToStationEmbedded.apply(requestDTO.getStation()),
                Collections.emptyList(),
                requestDTO.getStatus(),
                false,
                Instant.now()
        );

        final OrderEntity savedEntity = orderRepository.save(orderEntity);
        return orderEntityToOrderResponseDTO.apply(savedEntity);
    }

    public OrderResponseDTO addPositionForId(final Authentication authentication, final long id, final OrderPositionRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ORDER)) {
            throw new ForbiddenException();
        }
        OrderEntity orderEntity = orderRepository.getReferenceById(id);
        final StationResponseDTO stationDTO = catalogServiceClient.getStationById(appToken, orderEntity.getStation().getId());
        final StationResponseDTO.Service service = stationDTO.getServices().stream()
                .filter(o -> o.getId() == requestDTO.getServiceId())
                .findFirst()
                .orElseThrow(ServiceAlreadyExistsException::new);

        OrderPositionEntity orderPositionEntity = new OrderPositionEntity(
                0,
                new OrderPositionEntity.ServiceEmbedded(service.getId(), service.getName()),
                requestDTO.getPrice(),
                orderEntity
        );
        OrderPositionEntity savedEntity = orderPositionRepository.save(orderPositionEntity); // CASCADE
        return orderEntityToOrderResponseDTO.apply(orderEntity);
    }

    // /account/.../cards/...
    public OrderResponseDTO removePositionForId(final Authentication authentication, final long id, final long positionId) {
        if (!authentication.hasRole(Roles.ROLE_ORDER)) {
            throw new ForbiddenException();
        }
        OrderEntity orderEntity = orderRepository.getReferenceById(id);
        // TODO: check role & stationId
        OrderPositionEntity orderPositionEntity = orderPositionRepository.findById(positionId).orElseThrow(RuntimeException::new);
        if (orderPositionEntity.getOrder().getId() != orderEntity.getId()) {
            // TODO: hacker!!!
        }

        orderPositionRepository.delete(orderPositionEntity);
        orderEntity.getPositions().remove(orderPositionEntity);

        return orderEntityToOrderResponseDTO.apply(orderEntity);
    }

    public OrderResponseDTO finishById(final Authentication authentication, final long id) {
        // TODO: check role & stationId
        if (!authentication.hasRole(Roles.ROLE_ORDER)) {
            throw new ForbiddenException();
        }
        final OrderEntity orderEntity = orderRepository.getReferenceById(id);
        orderEntity.setStatus("завершён");

        sendToKafka(orderEntity);

        return orderEntityToOrderResponseDTO.apply(orderEntity);
    }

    public void deleteById(Authentication authentication, long id) {
        if (!authentication.hasRole(Roles.ROLE_ORDER)) {
            throw new ForbiddenException();
        }
        orderRepository.deleteById(id);
    }

    public void sendToKafka(final OrderEntity orderEntity) {
        final OrderEntity.UserEmbedded user = orderEntity.getUser();
        final OrderEntity.StationEmbedded station = orderEntity.getStation();
        final OrderMessage message = OrderMessage.builder()
                .id(orderEntity.getId())
                .user(
                        OrderMessage.User.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .build()
                )
                .station(
                        OrderMessage.Station.builder()
                                .id(station.getId())
                                .name(station.getName())
                                .build()
                )
                .positions(
                        orderEntity.getPositions().stream()
                                .map(o -> OrderMessage.Position.builder()
                                        .id(o.getId())
                                        .service(OrderMessage.Position.Service.builder()
                                                .id(o.getService().getId())
                                                .name(o.getService().getName())
                                                .build())
                                        .price(o.getPrice())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .status(orderEntity.getStatus())
                .created(orderEntity.getCreated())
                .build();

        kafkaTemplate.send("orders", message).addCallback(
                result -> {
                    log.debug("sent to kafka: {}", message);
                    orderRepository.markNotified(message.getId());
                },
                e -> {
                    log.error("can't send to kafka: {}", message, e);
                }
        );
    }

    @Scheduled(fixedDelay = 5_000)
    public void resendToKafka() {
        orderRepository.findTop5ByNotifiedIsFalseAndStatusOrderById("завершён").forEach(this::sendToKafka);
    }
}