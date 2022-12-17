package org.j2lab.schema.repository.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.j2lab.schema.repository.data.SchemaRepository;
import org.j2lab.schema.repository.model.Schema;
import org.j2lab.schema.repository.model.SchemaRequest;
import org.j2lab.schema.repository.model.SchemaResponse;
import org.j2lab.schema.repository.service.SchemaSender;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schemas")
public class SchemaController {
    private final SchemaSender schemaSender;
    private final SchemaRepository schemaRepository;

    @GetMapping("/{id}")
    private Mono<ResponseEntity<String>> getSchemaById(@PathVariable long id) {
        Optional<Schema> schemaOptional = schemaRepository.findById(id);
        if(schemaOptional.isPresent()) {
            return Mono.just(
                    ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(schemaOptional.get().toString())
            );
        } else {
            return Mono.just(
                    ResponseEntity.notFound()
                            .build()
            );
        }
    }

    @PostMapping("/{id}")
    private Mono<ResponseEntity<String>> postSchemaById(@PathVariable long id, @RequestBody JsonNode schema) {
        Mono<SchemaResponse> schemaResponse = schemaSender.send(new SchemaRequest(id, schema));
        return schemaResponse.map(resp -> {
            if(resp.getError() == null){
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resp.getMessage().getMessage().toString());
            } else {
                return ResponseEntity.internalServerError()
                        .build();
            }
        });
    }
}
