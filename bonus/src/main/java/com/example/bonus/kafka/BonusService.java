package com.example.bonus.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.example.bonus.model.TaskMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BonusService {
  @KafkaListener(topics = "tasks")
  public void listen(final ConsumerRecord<String, TaskMessage> record, final Acknowledgment acknowledgment) {
    log.info("event received: {}", record);
    acknowledgment.acknowledge();
  }
}
