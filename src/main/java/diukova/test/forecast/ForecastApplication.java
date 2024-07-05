package diukova.test.forecast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForecastApplication {
    private static final Logger logger = LoggerFactory.getLogger(
            ForecastApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ForecastApplication.class, args);

        logger.info("Application started!");
    }

}
