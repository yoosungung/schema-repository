package org.j2lab.schema.repository.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class SchemaRequest {
    private final String topic;
    private final Long key;
    private final JsonNode message;

    public SchemaRequest(Long key, JsonNode schema) {
        this.topic = "_schemas";
        this.key = key;
        this.message = schema;
    }
    public SchemaRequest(JsonNode schema) {
        this.topic = "_schemas";
        this.key = null;
        this.message = schema;
    }

}
