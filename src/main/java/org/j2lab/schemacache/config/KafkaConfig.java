package org.j2lab.schemacache.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties properties;
    private final SchemaProperties schemaProperties;

    @Bean("KafkaSender")
    public KafkaSender<String, Object> kafkaSender() {
        SenderOptions<String, Object> senderOptions = SenderOptions.create(getProducerProperties());
        senderOptions.scheduler(Schedulers.parallel());
        senderOptions.closeTimeout(Duration.ofSeconds(5));
        return KafkaSender.create(senderOptions);

    }

    @Bean("SchemaReceiver")
    public KafkaReceiver<Integer, String> SchemaReceiver() {
        ReceiverOptions<Integer, String> receiverOptions = ReceiverOptions.<Integer, String>create(getConsumerProperties());
        receiverOptions.subscription(Collections.singleton(schemaProperties.getTopic()));
        return KafkaReceiver.create(receiverOptions);
    }

    private Map<String, Object> getConsumerProperties() {
        return new HashMap<String, Object>() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
            put(ConsumerConfig.GROUP_ID_CONFIG, schemaProperties.getGroupId());
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
        }};
    }

    private Map<String, Object> getProducerProperties() {
        return new HashMap<String, Object>() {{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
        }};
    }
}
