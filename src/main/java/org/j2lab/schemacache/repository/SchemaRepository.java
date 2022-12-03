package org.j2lab.schemacache.repository;

import org.j2lab.schemacache.model.KafkaResult;
import org.j2lab.schemacache.model.Schema;
import reactor.core.publisher.Mono;

public interface SchemaRepository {
    Mono<Schema> getById(String id);
    Mono<KafkaResult> updateById(String id, Schema schema);
}
