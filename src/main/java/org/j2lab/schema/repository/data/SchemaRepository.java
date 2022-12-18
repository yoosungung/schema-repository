package org.j2lab.schema.repository.data;

import org.j2lab.schema.repository.model.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface SchemaRepository extends JpaRepository<Schema, Long> {
    @Query(value = "SELECT DISTINCT s.subject FROM Schema s")
    List<String> findSubject();

    @Query(value = "SELECT s.version FROM Schema s WHERE s.subject = :subject")
    List<Long> findVersionBySubject(@Param("subject") String subject);

    @Query(value = "SELECT s FROM Schema s WHERE s.subject = :subject AND s.version = :version")
    Optional<Schema> findVersionBySubjectAndVersion(@Param("subject") String subject, @Param("version") long version);

    @Query(value = "SELECT s.schema FROM Schema s WHERE s.subject = :subject AND s.version = :version")
    Optional<String> findSchemaBySubjectAndVersion(@Param("subject") String subject, @Param("version") long version);
}
