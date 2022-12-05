package org.j2lab.schema_cache.repository.cache;

import lombok.RequiredArgsConstructor;
import org.j2lab.schema_cache.model.SchemaRequest;
import org.j2lab.schema_cache.model.SchemaResponse;
import org.j2lab.schema_cache.repository.SchemaRepository;
import org.j2lab.schema_cache.service.SchemaSender;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SchemaCacheRepository implements SchemaRepository {
    private final SchemaSender schemaSender;
    private final CacheManager cacheManager;

    @Override
    public Mono<String> getById(String id) {
        return Mono.justOrEmpty(cacheManager.getCache("schemas").get(id, String.class));
    }

    @Override
    public Mono<SchemaResponse> updateById(String id, String schema) {
        return schemaSender.send(new SchemaRequest(id, schema));
    }
}
