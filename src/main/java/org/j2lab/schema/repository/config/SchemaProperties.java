package org.j2lab.schema.repository.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "schema")
public class SchemaProperties {
    private String topic;
    private String groupId;

    private String gen_groupId;

    public String getTopic() {
        if(this.topic == null) {
            return "_schemas";
        } else {
            return this.topic;
        }
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        if(this.groupId == null) {
            return "consumer_group.schemas_cache";
        } else {
            return this.groupId;
        }
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String genGroupId() {
        if(this.gen_groupId == null) {
            return this.getGroupId() + String.valueOf(new Date().getTime());
        }
        return this.gen_groupId;
    }
}
