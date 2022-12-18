package org.j2lab.schema.repository.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

@Getter
public class SchemaRequest {
    private final String topic;
    private final long key;
    private final JsonNode message;

    public SchemaRequest(long key, JsonNode schema) {
        this.topic = "_schemas";
        this.key = key;
        this.message = schema;
        ((ObjectNode)this.message).put("id", this.key);
    }
    public SchemaRequest(JsonNode schema) {
        this.topic = "_schemas";
        this.key = schema.get("id").asLong();
        this.message = schema;
    }

}
