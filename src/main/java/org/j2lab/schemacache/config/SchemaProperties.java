package org.j2lab.schemacache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "schema")
public class SchemaProperties {
    private String topic;
    private String groupId;
}
