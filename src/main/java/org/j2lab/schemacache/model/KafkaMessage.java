package org.j2lab.schemacache.model;

import lombok.Getter;

@Getter
public class KafkaMessage {
    private String topic;
    private String key;
    private Object message;

    public KafkaMessage(String key, Object message) {
        this.topic = "_schemas";
        this.key = key;
        this.message = message;
    }
    public KafkaMessage(Object message) {
        this.topic = "_schemas";
        this.key = null;
        this.message = message;
    }

}
