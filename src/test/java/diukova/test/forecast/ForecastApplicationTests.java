package diukova.test.forecast;

import diukova.test.forecast.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ForecastApplicationTests {

    @Autowired
    CityService cityService;

    @Test
    void contextLoads() {
    }

    @Test
    void getCities() {
        int size = cityService.getCities().size();

        Assertions.assertEquals(8, size);
    }

    //TODO add test for forecast request

}
