package org.j2lab.schema.repository.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.j2lab.schema.repository.model.SchemaRequest;
import org.j2lab.schema.repository.model.SchemaResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;

@Service
@RequiredArgsConstructor
public class SchemaSender {
    private final KafkaSender<Long, JsonNode> producer;

    public Mono<SchemaResponse> send(SchemaRequest message) {
        return producer.createOutbound()
                .send(Mono.just(new ProducerRecord<>(message.getTopic(), message.getKey(), message.getMessage())))
                .then()
                .thenReturn(new SchemaResponse(message))
                .onErrorResume(error -> Mono.just(new SchemaResponse(message, error)));
    }
}
