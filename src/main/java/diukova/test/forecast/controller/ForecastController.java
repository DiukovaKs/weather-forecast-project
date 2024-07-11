package diukova.test.forecast.controller;

import diukova.test.forecast.service.WeatherForecastService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ForecastController {

    private final WeatherForecastService weatherForecastService;

    /*Get wind info for default city (Wellington)*/
    @GetMapping("/wind")
    public String getWindInfo() {
        return weatherForecastService.getWindForecastMessage(null);
    }

    /*Get weather info for default city (Wellington)*/
    @GetMapping("/weather")
    public String getForecast() {
        return weatherForecastService.getWeatherMessage(null);
    }
}
