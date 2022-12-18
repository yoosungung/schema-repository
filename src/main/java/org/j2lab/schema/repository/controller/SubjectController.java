package org.j2lab.schema.repository.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.j2lab.schema.repository.data.SchemaRepository;
import org.j2lab.schema.repository.model.Schema;
import org.j2lab.schema.repository.model.SchemaRequest;
import org.j2lab.schema.repository.model.SchemaResponse;
import org.j2lab.schema.repository.service.LastOffset;
import org.j2lab.schema.repository.service.SchemaSender;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SchemaSender schemaSender;
    private final LastOffset lastOffset;
    private final SchemaRepository schemaRepository;

    @GetMapping()
    private Mono<ResponseEntity<String>> getSubjectAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(schemaRepository.findSubject().toString())
        );
    }

    @GetMapping("/{subject}/versions")
    private Mono<ResponseEntity<String>> getVersionsBySubject(@PathVariable String subject) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(schemaRepository.findVersionBySubject(subject).toString())
        );
    }

    @GetMapping("/{subject}/versions/latest")
    private Mono<ResponseEntity<String>> getBySubjectAndLatest(@PathVariable String subject) {
        List<Long> versionList = schemaRepository.findVersionBySubject(subject);
        long version = getVersion(versionList);
        if(version > 0) {
            return this.getBySubjectAndVersion(subject, version);
        } else {
            return Mono.just(
                    ResponseEntity.notFound().build()
            );
        }
    }

    private static long getVersion(List<Long> versionList) {
        long version = -1;
        for(long v : versionList) {
            if(v > version) {
                version = v;
            }
        }
        return version;
    }

    @GetMapping("/{subject}/versions/{version}")
    private Mono<ResponseEntity<String>> getBySubjectAndVersion(@PathVariable String subject, @PathVariable long version) {
        Optional<Schema> schemaOptional = schemaRepository.findVersionBySubjectAndVersion(subject, version);
        if(schemaOptional.isPresent()) {
            return Mono.just(
                    ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(schemaOptional.get().toString())
            );
        } else {
            return Mono.just(
                    ResponseEntity.notFound().build()
            );
        }
    }

    @GetMapping("/{subject}/versions/latest/schema")
    private Mono<ResponseEntity<String>> getSchemaBySubjectAndLatest(@PathVariable String subject) {
        List<Long> versionList = schemaRepository.findVersionBySubject(subject);
        long version = getVersion(versionList);
        if(version > 0) {
            Optional<String> schemaOptional = schemaRepository.findSchemaBySubjectAndVersion(subject, version);
            if(schemaOptional.isPresent()) {
                return Mono.just(
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(schemaOptional.get())
                );
            } else {
                return Mono.just(
                        ResponseEntity.notFound().build()
                );
            }
        } else {
            return Mono.just(
                    ResponseEntity.notFound().build()
            );
        }
    }

    @PostMapping("/{subject}/versions")
    private Mono<ResponseEntity<String>> postSubject(@PathVariable String subject, @RequestBody JsonNode body) {
        long id = lastOffset.get();
        ((ObjectNode)body).put("subject", subject);
        Mono<SchemaResponse> schemaResponse = schemaSender.send(new SchemaRequest(id, body));
        return schemaResponse.map(resp -> {
            if(resp.getError() == null){
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(String.format("{\"id\": %d}", id));
            } else {
                return ResponseEntity.internalServerError().build();
            }
        });
    }
}
