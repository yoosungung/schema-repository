package org.j2lab.schema_cache.controller;

import lombok.RequiredArgsConstructor;
import org.j2lab.schema_cache.repository.SchemaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schemas")
public class SchemaController {
    private final SchemaRepository schemaRepository;

    @GetMapping("/{id}")
    private Mono<ResponseEntity<String>> getSchemaById(@PathVariable String id) {
        return schemaRepository.getById(id).map(result ->{
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toJsonString());
        });
    }

    @PostMapping("/{id}")
    private Mono<ResponseEntity<String>> postSchemaById(@PathVariable String id, @RequestBody String schema) {
        return schemaRepository.updateById(id, schema).map(result -> {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(result.toJsonString());
            });
    }
}
