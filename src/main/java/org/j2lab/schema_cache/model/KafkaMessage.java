package org.j2lab.schema_cache.model;

import lombok.Getter;

@Getter
public class KafkaMessage {
    private final String topic;
    private final String key;
    private final String message;

    public KafkaMessage(String key, String message) {
        this.topic = "_schemas";
        this.key = key;
        this.message = message;
    }
    public KafkaMessage(String message) {
        this.topic = "_schemas";
        this.key = null;
        this.message = message;
    }

}
