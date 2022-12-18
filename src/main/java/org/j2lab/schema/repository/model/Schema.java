package org.j2lab.schema.repository.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name="schemas", uniqueConstraints={@UniqueConstraint(columnNames={"subject","version"})})
@Entity
public class Schema {
    @Id
    private long id;

    private long version;

    private String subject;

    private String schema;

    public Schema(JsonNode body) {
        this.subject = body.get("subject").asText();
        this.version = (body.has("version")?body.get("version").asLong():1);
        this.id = body.get("id").asLong();
        this.schema = body.get("schema").asText();
    }

    public Schema(String subject, long id, String schema) {
        this.subject = subject;
        this.version = 1;
        this.id = id;
        this.schema = schema;
    }

    public Schema(String subject, long version, long id, String schema) {
        this.subject = subject;
        this.version = version;
        this.id = id;
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Schema schema = (Schema) o;
        return id == schema.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(schema);
    }

    @Override
    public String toString() {
        return "{\"subject\": \""+subject+"\","
                +"\"version\": "+String.valueOf(version)+","
                +"\"id\": "+String.valueOf(id)+","
                +"\"schema\": \""+schema.replace("\"", "\\\"")+"\"}";
    }
}
