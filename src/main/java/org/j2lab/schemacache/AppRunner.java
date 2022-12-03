package org.j2lab.schemacache;

import lombok.RequiredArgsConstructor;
import org.j2lab.schemacache.repository.SchemaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {
    private final SchemaRepository schemaRepository;

    @Override
    public void run(String... args) throws Exception {
        // TODO: load from kafka topic "_schemas"
    }
}
