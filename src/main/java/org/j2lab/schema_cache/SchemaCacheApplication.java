package org.j2lab.schema_cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class SchemaCacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchemaCacheApplication.class, args);
	}
}
