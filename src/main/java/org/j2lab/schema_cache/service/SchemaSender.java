package org.j2lab.schema_cache.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.j2lab.schema_cache.model.SchemaRequest;
import org.j2lab.schema_cache.model.SchemaResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;

@Service
@RequiredArgsConstructor
public class SchemaSender {
    private final KafkaSender<String, Object> producer;

    public Mono<SchemaResponse> send(SchemaRequest message) {
        return producer.createOutbound()
                .send(Mono.just(new ProducerRecord<>(message.getTopic(), message.getKey(), message.getMessage())))
                .then()
                .thenReturn(new SchemaResponse(message))
                .onErrorResume(error -> Mono.just(new SchemaResponse(message, error)));
    }
}
