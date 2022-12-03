package org.j2lab.schemacache.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.j2lab.schemacache.model.KafkaMessage;
import org.j2lab.schemacache.model.KafkaResult;
import org.j2lab.schemacache.model.Schema;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaSender<String, Object> producer;

    public Mono<KafkaResult> send(KafkaMessage message) {
        return producer.createOutbound()
                .send(Mono.just(new ProducerRecord<>(message.getTopic(), message.getKey(), message.getMessage())))
                .then()
                .thenReturn(new KafkaResult(message))
                .onErrorResume(error -> Mono.just(new KafkaResult(message, error)));
    }
}
