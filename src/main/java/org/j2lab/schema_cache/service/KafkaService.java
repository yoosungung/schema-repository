package org.j2lab.schema_cache.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.j2lab.schema_cache.model.KafkaMessage;
import org.j2lab.schema_cache.model.SchemaResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaSender<String, Object> producer;

    public Mono<SchemaResult> send(KafkaMessage message) {
        return producer.createOutbound()
                .send(Mono.just(new ProducerRecord<>(message.getTopic(), message.getKey(), message.getMessage())))
                .then()
                .thenReturn(new SchemaResult(message))
                .onErrorResume(error -> Mono.just(new SchemaResult(message, error)));
    }
}
