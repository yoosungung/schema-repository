package org.j2lab.schema_cache.repository.cache;

import lombok.RequiredArgsConstructor;
import org.j2lab.schema_cache.model.KafkaMessage;
import org.j2lab.schema_cache.model.SchemaResult;
import org.j2lab.schema_cache.repository.SchemaRepository;
import org.j2lab.schema_cache.service.KafkaService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SchemaCacheRepository implements SchemaRepository {
    private final KafkaService kafkaService;
    @Override
    @Cacheable("schemas")
    public Mono<SchemaResult> getById(String id) {
        return Mono.empty();
    }

    @Override
    public Mono<SchemaResult> updateById(String id, String schema) {
        return kafkaService.send(new KafkaMessage(schema));
    }
}
