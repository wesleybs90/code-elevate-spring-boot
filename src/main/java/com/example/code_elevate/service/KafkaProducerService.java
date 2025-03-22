package com.example.code_elevate.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 2000))
  public void sendMessage(String topic, String message) {
    kafkaTemplate.send(topic, message);
  }
}