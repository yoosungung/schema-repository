package org.j2lab.schemacache.model;

import lombok.Data;

@Data
public class KafkaResult {
    private final KafkaMessage message;
    private final Throwable error;

    public KafkaResult(KafkaMessage message, Throwable error) {
        this.message = message;
        this.error = error;
    }

    public KafkaResult(KafkaMessage message) {
        this.message = message;
        this.error = null;
    }

    public boolean hasError() {
        return error != null;
    }
}
