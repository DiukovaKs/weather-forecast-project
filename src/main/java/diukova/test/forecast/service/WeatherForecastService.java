package diukova.test.forecast.service;

import diukova.test.forecast.dao.CityEntity;
import diukova.test.forecast.dto.request.ForecastServiceUrlParametersDto;
import diukova.test.forecast.dto.response.CurrentForecastDto;
import diukova.test.forecast.dto.response.WeatherForecastDto;
import diukova.test.forecast.mapper.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final CityService service;
    @Autowired
    private final ChatToCityService chatToCityService;

    final RestTemplate restTemplate = new RestTemplate();

    public CurrentForecastDto getForecast() {
        return getCurrentWeather(null);
    }

    public String getWeatherMessage(Long chatId) {
        CurrentForecastDto dto = getCurrentWeather(chatId);
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

    public String getWindForecastMessage(Long chatId) {
        CurrentForecastDto dto = getCurrentWeather(chatId);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM");

        String forecastMessage = "Wind forecast for " + dateFormat.format(LocalDateTime.now()) + "\n" +
                " 8 AM     " + dto.getWindDirection()[0] + "     " + dto.getWindSpeed()[0] + " m/s \n" +
                " 12 PM   " + dto.getWindDirection()[1] + "     " + dto.getWindSpeed()[1] + " m/s \n" +
                " 4 PM     " + dto.getWindDirection()[2] + "     " + dto.getWindSpeed()[2] + " m/s \n" +
                " 8 PM     " + dto.getWindDirection()[3] + "     " + dto.getWindSpeed()[3] + " m/s \n";

        log.debug(forecastMessage);

        return forecastMessage;
    }

    private CurrentForecastDto getCurrentWeather(Long chatId) {
        WeatherForecastDto dto;
        if (chatId == null) {
            dto = restTemplate.getForObject(forecastUrlBuilder.getDefaultURL(), WeatherForecastDto.class);
        } else {
            Long cityId = chatToCityService.getCityId(chatId);
            CityEntity city = service.getCityById(cityId);

            ForecastServiceUrlParametersDto parameters = forecastUrlBuilder.getDefaultParameters();
            parameters.setLatitude(city.getLatitude().replace(',', '.'));
            parameters.setLongitude(city.getLongitude().replace(',', '.'));

            String url = forecastUrlBuilder.createForecastUrl(parameters);

            dto = restTemplate.getForObject(url, WeatherForecastDto.class);
        }

        return mapper.toCurrentForecastDto(dto);
    }
}
