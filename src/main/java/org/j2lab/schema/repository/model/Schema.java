package org.j2lab.schema.repository.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name="schemas")
@Entity
public class Schema {
    @Id
    private long id;

    private String name;

    private String schema;

    public Schema(long id, JsonNode schema) {
        this.id = id;
        this.schema = schema.toString();
        this.name = schema.get("schema").get("name").asText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Schema schema = (Schema) o;
        return id == schema.id && name.equals(schema.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "schema {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
