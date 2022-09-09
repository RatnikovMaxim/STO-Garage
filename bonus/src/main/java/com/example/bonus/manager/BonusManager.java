package com.example.bonus.manager;

import com.example.bonus.dto.BonusResponseDTO;
import com.example.bonus.entity.BonusEntity;
import com.example.bonus.model.OrderMessage;
import com.example.bonus.repository.BonusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BonusManager {
    private final BonusRepository bonusRepository;

    public List<BonusResponseDTO> getAll() {
        return null;
    }

    public BonusResponseDTO getById(Authentication authentication, long id) {
        return null;
    }

    // Это аналог сохранения (т.е. create)
    @KafkaListener(topics = "orders")
    public void listen(final ConsumerRecord<String, OrderMessage> record, final Acknowledgment acknowledgment) {
        log.info("event received: {}", record);
        acknowledgment.acknowledge();

        final OrderMessage orderMessage = record.value();
        if (!orderMessage.getStatus().equals("завершён")) {
            return;
        }

        final long bonusSum = orderMessage.getPositions().stream()
                .mapToLong(OrderMessage.Position::getPrice)
                .sum() / 100;

        final OrderMessage.User user = orderMessage.getUser();
        final Instant now = Instant.now();
        final BonusEntity bonusEntity = new BonusEntity(
                0,
                new BonusEntity.UserEmbedded(user.getId(), user.getName()),
                orderMessage.getId(),
                bonusSum,
                now,
                now.plus(30, ChronoUnit.DAYS)
        );

        bonusRepository.save(bonusEntity);
    }
}
