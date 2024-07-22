package diukova.test.forecast.service;

import diukova.test.forecast.dao.CityEntity;
import diukova.test.forecast.dto.request.ForecastServiceUrlParametersDto;
import diukova.test.forecast.dto.response.CurrentForecastDto;
import diukova.test.forecast.dto.response.WeatherForecastDto;
import diukova.test.forecast.mapper.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherForecastService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM")
            .localizedBy(new Locale("en_NZ"));
    public static final Long DEFAULT_CITY_ID = 1L;
    public static final String DEFAULT_CITY_NAME = "Wellington";

    private final DtoMapper mapper;
    private final ForecastUrlBuilder forecastUrlBuilder;
    private final WeatherDataService weatherDataService;
    private final CityService service;
    private final ChatToCityService chatToCityService;

    public CurrentForecastDto getForecast() {
        try {
            return getCurrentWeather(null);
        } catch (ExecutionException e) {
            return null;
        }
    }

    public String getWeatherMessage(Long chatId) {

        String forecastMessage = null;
        try {
            CurrentForecastDto dto = getCurrentWeather(chatId);

            String header = String.format("""
                    <b>Weather forecast for %s %s</b>
                    """, dto.getCity(), FORMATTER.format(LocalDateTime.now()));

            String lines = String.format("""
                     Time     Temp.°C      Hum. %%       UV
                      8 AM      %s  %s        %s          %s
                     12 PM      %s  %s        %s          %s
                      4 PM      %s  %s        %s          %s
                      8 PM      %s  %s        %s          %s
                    """, dto.getTemperature()[0], dto.getWeatherCode()[0], dto.getHumidity()[0], dto.getUvIndex()[0],
                    dto.getTemperature()[1], dto.getWeatherCode()[1], dto.getHumidity()[1], dto.getUvIndex()[1],
                    dto.getTemperature()[2], dto.getWeatherCode()[2], dto.getHumidity()[2], dto.getUvIndex()[2],
                    dto.getTemperature()[3], dto.getWeatherCode()[3], dto.getHumidity()[3], dto.getUvIndex()[3]);

            forecastMessage = header + lines;
        } catch (ExecutionException e) {
            forecastMessage = e.getMessage();
        }

        log.debug(forecastMessage);

        return forecastMessage;
    }

    public String getWindForecastMessage(Long chatId) {
        String forecastMessage;

        try {
            CurrentForecastDto dto = getCurrentWeather(chatId);

            String header = String.format("""
                <b>Wind forecast for %s %s</b>
                """, dto.getCity(), FORMATTER.format(LocalDateTime.now()));
            String lines = String.format("""
                  8 AM      %s      %s  m/s
                 12 PM      %s      %s  m/s
                  4 PM      %s      %s  m/s
                  8 PM      %s      %s  m/s
                """, dto.getWindDirection()[0], dto.getWindSpeed()[0],
                    dto.getWindDirection()[1], dto.getWindSpeed()[1],
                    dto.getWindDirection()[2], dto.getWindSpeed()[2],
                    dto.getWindDirection()[3], dto.getWindSpeed()[3]);

            forecastMessage = header + lines;
        } catch (ExecutionException e) {
            forecastMessage = e.getMessage();
        }

        log.debug(forecastMessage);

        return forecastMessage;
    }

    private CurrentForecastDto getCurrentWeather(Long chatId) throws ExecutionException {
        WeatherForecastDto dto;

        if (chatId == null) {
            dto = weatherDataService.getWeatherData(forecastUrlBuilder.getDefaultURL(), DEFAULT_CITY_ID, null);

            Objects.requireNonNull(dto).setCity(DEFAULT_CITY_NAME);
        } else {
            Long cityId = chatToCityService.getCityId(chatId);

            if (cityId == null || cityId == 0) {
                throw new ExecutionException("Choose city previously", new Throwable("ChatToCityTable is empty"));
            }

            CityEntity city = service.getCityById(cityId);

            ForecastServiceUrlParametersDto parameters = forecastUrlBuilder.getDefaultParameters();
            parameters.setLatitude(city.getLatitude().replace(',', '.'));
            parameters.setLongitude(city.getLongitude().replace(',', '.'));

            String url = forecastUrlBuilder.createForecastUrl(parameters);

            dto = weatherDataService.getWeatherData(url, cityId, chatId);

            if (dto != null) {
                dto.setCity(city.getCity());
            }
        }

        return mapper.toCurrentForecastDto(dto);
    }
}
