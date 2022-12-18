package org.j2lab.schema.repository.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.j2lab.schema.repository.data.SchemaRepository;
import org.j2lab.schema.repository.model.Schema;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
public class SchemaReceiver implements ApplicationRunner {
    private final KafkaReceiver<Long, JsonNode> receiver;
    private final SchemaRepository schemaRepository;

    @Override
    public void run(ApplicationArguments args) {
        receiver.receive()
                .subscribe(receiverRecord ->{
                    JsonNode body = receiverRecord.value();
                    if(body != null) {
                        schemaRepository.save(new Schema(body));
                    }
                });
    }
}
