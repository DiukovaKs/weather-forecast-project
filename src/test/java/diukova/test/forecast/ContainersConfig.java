package diukova.test.forecast;

import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@Configuration
public class ContainersConfig {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15.4")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("schema.sql");

    static {
        postgresContainer.start();
        var mappedPort = postgresContainer.getMappedPort(5432);
        System.setProperty("postgres.container.port", String.valueOf(mappedPort));

        System.out.println("Test postgres container started");
    }

    public static String getJdbcUrl() {
        return postgresContainer.getJdbcUrl();
    }

    public static String getUsername() {
        return postgresContainer.getUsername();
    }

    public static String getPassword() {
        return postgresContainer.getPassword();
    }

    public static PostgreSQLContainer<?> getPostgresContainer() {
        return postgresContainer;
    }

}
