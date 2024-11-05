package diukova.test.forecast;

import diukova.test.forecast.service.CityService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
class ForecastApplicationTests {

    @Autowired
    CityService cityService;

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", ContainersConfig::getJdbcUrl);
        registry.add("spring.datasource.username", ContainersConfig::getUsername);
        registry.add("spring.datasource.password", ContainersConfig::getPassword);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getCities() {
        int size = cityService.getCities().size();

        Assertions.assertEquals(8, size);
    }

    //TODO add test for forecast request

    @AfterAll
    static void teardown() {
        ContainersConfig.getPostgresContainer().stop();
    }
}
