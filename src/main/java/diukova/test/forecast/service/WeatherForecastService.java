package diukova.test.forecast.service;

import diukova.test.forecast.dto.response.CurrentForecastDto;
import diukova.test.forecast.dto.response.WeatherForecastDto;
import diukova.test.forecast.mapper.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherForecastService {

    private final DtoMapper mapper;
    private final ForecastUrlBuilder forecastUrlBuilder;
    final RestTemplate restTemplate = new RestTemplate();

    public CurrentForecastDto getForecast() {
        return getCurrentWeather();
    }

    public String getWeatherMessage() {
        CurrentForecastDto dto = getCurrentWeather();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM");

        String forecastMessage = "Weather for " + dateFormat.format(LocalDateTime.now()) + "\n" +
                " Time     Temp. °C          Hum. %  Precipt. % \n" +
                " 8 AM     " + dto.getTemperature()[0] + " °C   " + dto.getWeatherCode()[0] + "       " + dto.getHumidity()[0] + " %      " + dto.getPrecipitation()[0] + " % \n" +
                " 12 PM   " + dto.getTemperature()[1] + " °C   " + dto.getWeatherCode()[1] + "       " + dto.getHumidity()[1] + " %      " + dto.getPrecipitation()[1] + " % \n" +
                " 4 PM     " + dto.getTemperature()[2] + " °C   " + dto.getWeatherCode()[2] + "       " + dto.getHumidity()[2] + " %      " + dto.getPrecipitation()[2] + " % \n" +
                " 8 PM     " + dto.getTemperature()[3] + " °C   " + dto.getWeatherCode()[3] + "       " + dto.getHumidity()[3] + " %      " + dto.getPrecipitation()[3] + " % \n";

        log.debug(forecastMessage);

        return forecastMessage;
    }

    public String getWindForecastMessage() {
        CurrentForecastDto dto = getCurrentWeather();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM");

        String forecastMessage = "Wind forecast for " + dateFormat.format(LocalDateTime.now()) + "\n" +
                " 8 AM     " + dto.getWindDirection()[0] + "     " + dto.getWindSpeed()[0] + " m/s \n" +
                " 12 PM   " + dto.getWindDirection()[1] + "     " + dto.getWindSpeed()[1] + " m/s \n" +
                " 4 PM     " + dto.getWindDirection()[2] + "     " + dto.getWindSpeed()[2] + " m/s \n" +
                " 8 PM     " + dto.getWindDirection()[3] + "     " + dto.getWindSpeed()[3] + " m/s \n";

        log.debug(forecastMessage);

        return forecastMessage;
    }

    private CurrentForecastDto getCurrentWeather() {
        final WeatherForecastDto dto = restTemplate.getForObject(forecastUrlBuilder.getDefaultURL(), WeatherForecastDto.class);

        return mapper.toCurrentForecastDto(dto);
    }
}
