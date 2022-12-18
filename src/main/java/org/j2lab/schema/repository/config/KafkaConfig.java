package org.j2lab.schema.repository.config;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.j2lab.schema.repository.service.LastOffset;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties properties;
    private final SchemaProperties schemaProperties;

    @Bean("KafkaSender")
    public KafkaSender<Long, JsonNode> kafkaSender() {
        SenderOptions<Long, JsonNode> senderOptions = SenderOptions.create(this.getProducerProperties());
        senderOptions.scheduler(Schedulers.parallel());
        senderOptions.closeTimeout(Duration.ofSeconds(5));
        return KafkaSender.create(senderOptions);

    }

    @Bean("KafkaReceiver")
    public KafkaReceiver<Long, JsonNode> kafkaReceiver() {
        ReceiverOptions<Long, JsonNode> receiverOptions = ReceiverOptions.<Long, JsonNode>create(this.getConsumerProperties())
                .subscription(Collections.singleton(schemaProperties.getTopic()));
        return KafkaReceiver.create(receiverOptions);
    }

    @Bean("AdminClient")
    public AdminClient adminClient() {
        return KafkaAdminClient.create(this.getAdminProperties());
    }

    @Bean("LastOffset")
    public LastOffset lastOffset() {
        TopicPartition tp = new TopicPartition(schemaProperties.getTopic(), 0);
        Map<TopicPartition, OffsetSpec> requestOffsets = new HashMap<TopicPartition, OffsetSpec>(){{
            put(tp, OffsetSpec.latest());
        }};
        return new LastOffset() {
            @Override
            public long get() {
                Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> latestOffsets = null;
                try {
                    latestOffsets = adminClient().listOffsets(requestOffsets).all().get();
                    return latestOffsets.get(tp).offset();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private Map<String, Object> getProducerProperties() {
        return new HashMap<String, Object>() {{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
        }};
    }

    private Map<String, Object> getConsumerProperties() {
        return new HashMap<String, Object>() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
            put(ConsumerConfig.GROUP_ID_CONFIG, schemaProperties.genGroupId());
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            put(JsonDeserializer.TRUSTED_PACKAGES, "com.fasterxml.jackson.databind.node");
        }};
    }

    private Map<String, Object> getAdminProperties() {
        return new HashMap<String, Object>() {{
            put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
            put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
            put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 1000);
            put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 1000);
        }};
    }
}
