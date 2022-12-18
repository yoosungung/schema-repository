package org.j2lab.schema.repository.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.j2lab.schema.repository.data.SchemaRepository;
import org.j2lab.schema.repository.model.Schema;
import org.j2lab.schema.repository.service.SchemaSender;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/config")
public class ConfigController {
    private final SchemaSender schemaSender;
    private final SchemaRepository schemaRepository;

    @PutMapping("/{subject}")
    private Mono<ResponseEntity<String>> getBySubject(@PathVariable String subject, @RequestBody JsonNode body) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"compatibility\": \"BACKWARD\"}")
        );
    }
}
