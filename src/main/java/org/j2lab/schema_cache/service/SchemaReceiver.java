package org.j2lab.schema_cache.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.event.KafkaEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
public class SchemaReceiver implements ApplicationRunner {
    private final KafkaReceiver<String, String> receiver;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        receiver.receive()
                .doOnNext(message ->{
                    String id = message.key();
                    String schema = message.value();
                    if(id != null) {
                        cacheManager.getCache("schemas").put(id,  schema);
                    }
                })
                .subscribe();
    }
}
