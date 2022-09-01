package com.example.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.orders.entity.TaskEntity;
import com.example.orders.model.TaskMessage;
import com.example.orders.repository.TaskRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
  private final KafkaTemplate<String, TaskMessage> template;
  private final TaskRepository repository;

  public TaskMessage create(final TaskMessage request) {
    final TaskEntity entity = TaskEntity.builder()
        .id(UUID.randomUUID().toString())
        .data(request.getData())
        .created(Instant.now())
        .build();
    final TaskEntity saved = repository.save(entity);
    final TaskMessage response = TaskMessage.builder()
        .id(saved.getId())
        .data(saved.getData())
        .created(saved.getCreated().getEpochSecond())
        .build();

    sendToKafka(request);

    return response;
  }

  private void sendToKafka(final TaskMessage request) {
    template.send("tasks", request).addCallback(
        result -> {
          log.debug("sent to kafka: {}", request);
          repository.markSent(request.getId());
        },
        e -> {
          log.error("can't send to kafka: {}", request, e);
          repository.markSentError(request.getId());
        }
    );
  }

  @Scheduled(fixedDelay = 5_000)
  public void resendToKafka() {
    // TODO:
  }
}
