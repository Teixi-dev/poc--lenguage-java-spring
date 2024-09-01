package teixi.dev.poc.shared.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ActiveProfiles(value = {"testing"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestWithApplicationContainers {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    ResourceLoader resourceLoader;

    @Container
    private static final MySQLContainer<?> CONTAINER = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    static {
        CONTAINER.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    public void restartDatabase() throws IOException {
        executeQuery(loadFile("drop"));
        executeQuery(loadFile("schema"));
        executeQuery(loadFile("data"));
    }

    private void executeQuery(String query) {
        for (String value : query.split(";\r\n")) {
            if (!value.isBlank() || !value.isEmpty())
                jdbcTemplate.execute(value);
        }
    }

    private String loadFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(
                resourceLoader.getResource("classpath:" + fileName + ".sql").getURI()
        )));
    }
}
