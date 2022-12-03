package org.j2lab.schema_cache.model;

import lombok.Data;

@Data
public class SchemaResult {
    private final KafkaMessage message;
    private final Throwable error;

    public SchemaResult(KafkaMessage message, Throwable error) {
        this.message = message;
        this.error = error;
    }

    public SchemaResult(KafkaMessage message) {
        this.message = message;
        this.error = null;
    }

    public boolean hasError() {
        return error != null;
    }

    public String toJsonString() {
        if(hasError()) {

        } else {

        }
        return null;
    }
}
