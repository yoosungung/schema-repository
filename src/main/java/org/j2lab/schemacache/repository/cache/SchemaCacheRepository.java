package org.j2lab.schemacache.repository.cache;

import lombok.RequiredArgsConstructor;
import org.j2lab.schemacache.model.KafkaMessage;
import org.j2lab.schemacache.model.KafkaResult;
import org.j2lab.schemacache.model.Schema;
import org.j2lab.schemacache.repository.SchemaRepository;
import org.j2lab.schemacache.service.KafkaService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SchemaCacheRepository implements SchemaRepository {
    private final KafkaService kafkaService;
    @Override
    @Cacheable("schemas")
    public Mono<Schema> getById(String id) {
        return Mono.just(new Schema(id));
    }

    @Override
    public Mono<KafkaResult> updateById(String id, Schema schema) {
        return kafkaService.send(new KafkaMessage(schema))
                .map(kafkaResult -> {
                    if(kafkaResult.hasError()) {
                        //TODO: Something
                    }
                    return kafkaResult;
                });
    }
}
