package org.j2lab.schema_cache.repository;

import org.j2lab.schema_cache.model.SchemaResult;
import reactor.core.publisher.Mono;

public interface SchemaRepository {
    Mono<SchemaResult> getById(String id);
    Mono<SchemaResult> updateById(String id, String schema);
}
