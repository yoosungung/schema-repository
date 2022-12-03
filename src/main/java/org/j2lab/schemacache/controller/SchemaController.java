package org.j2lab.schemacache.controller;

import lombok.RequiredArgsConstructor;
import org.j2lab.schemacache.model.Schema;
import org.j2lab.schemacache.repository.SchemaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schemas")
public class SchemaController {
    private final SchemaRepository schemaRepository;

    @GetMapping("/{id}")
    private Mono<Schema> getSchemaById(@PathVariable String id) {
        return schemaRepository.getById(id);
    }
}
