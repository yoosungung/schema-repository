package org.j2lab.schema.repository.data;

import org.j2lab.schema.repository.model.Schema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaRepository extends JpaRepository<Schema, Long> {
}
