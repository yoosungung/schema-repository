package org.j2lab.schema.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class SchemaRepositoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchemaRepositoryApplication.class, args);
	}
}
