package org.j2lab.schema_cache.model;

import lombok.Getter;

@Getter
public class SchemaRequest {
    private final String topic;
    private final String key;
    private final String message;

    public SchemaRequest(String key, String message) {
        this.topic = "_schemas";
        this.key = key;
        this.message = message;
    }
    public SchemaRequest(String message) {
        this.topic = "_schemas";
        this.key = null;
        this.message = message;
    }

}
