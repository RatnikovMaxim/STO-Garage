package com.example.orders.service;

import com.example.orders.repository.OrderMessageRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.orders.model.OrderMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Builder
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderKafkaService {
    private final OrderMessageRepository orderMessageRepository;

    private final KafkaTemplate<String, OrderMessage> template;

    public void send(final OrderMessage message) {
        // TODO: собрать из message -> entity


        template.send("orders", message).addCallback(
                result -> {
                    log.debug("sent to kafka: {}", message);
                    // repository.markSent(message.getId());
                },
                e -> {
                    log.error("can't send to kafka: {}", message, e);
                    // repository.markSentError(message.getId());
                }
        );
    }

//    @Scheduled(fixedDelay = 5_000)
//    public void resendToKafka() {
//        // TODO: вытаскивать те, у кого error -> send
//    }
}
