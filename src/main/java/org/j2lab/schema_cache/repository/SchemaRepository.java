package org.j2lab.schema_cache.repository;

import org.j2lab.schema_cache.model.SchemaResponse;
import reactor.core.publisher.Mono;

public interface SchemaRepository {
    Mono<String> getById(String id);
    Mono<SchemaResponse> updateById(String id, String schema);
}
