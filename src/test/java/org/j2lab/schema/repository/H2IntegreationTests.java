package org.j2lab.schema.repository;

import org.j2lab.schema.repository.data.SchemaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class H2IntegreationTests {
    private static final String AN_EXPECTED_SCHEMA = buildSchema();

    @Autowired
    private SchemaRepository schemaRepository;

    @Test
    public void givenInitData_whenApplicationStarts_thenDataIsLoaded() {
        Iterable<String> schemas = schemaRepository.findAll();

        assertThat(schemas)
                .contains(AN_EXPECTED_SCHEMA);
    }

    private static String buildSchema() {
        String c = new String();
        c = "Canada";
        return c;
    }
}
