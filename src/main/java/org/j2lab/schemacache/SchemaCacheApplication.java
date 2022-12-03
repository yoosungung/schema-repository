package org.j2lab.schemacache;

import org.j2lab.schemacache.model.Schema;
import org.j2lab.schemacache.repository.SchemaRepository;
import org.j2lab.schemacache.repository.cache.SchemaCacheRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@EnableCaching
@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class SchemaCacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchemaCacheApplication.class, args);
	}
}
