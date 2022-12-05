package org.j2lab.schema_cache.model;

import lombok.Data;

@Data
public class SchemaResponse {
    private final SchemaRequest message;
    private final Throwable error;

    public SchemaResponse(SchemaRequest message, Throwable error) {
        this.message = message;
        this.error = error;
    }

    public SchemaResponse(SchemaRequest message) {
        this.message = message;
        this.error = null;
    }

    public String toString() {
        return null;
    }
}
