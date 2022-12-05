package org.j2lab.schema_cache.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
public class SchemaReceiver implements ApplicationRunner {
    private final KafkaReceiver<String, String> receiver;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) {
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
