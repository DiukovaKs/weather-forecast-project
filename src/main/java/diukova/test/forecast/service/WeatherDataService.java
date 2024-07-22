package diukova.test.forecast.service;

import diukova.test.forecast.dto.response.WeatherForecastDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@CacheConfig(cacheNames = "weatherCache")
public class WeatherDataService {

    final RestTemplate restTemplate = new RestTemplate();

    @Cacheable(cacheNames = "weather", key = "#chatId + #cityId")
    public WeatherForecastDto getWeatherData(String url, Long cityId, Long chatId) {
        return restTemplate.getForObject(url, WeatherForecastDto.class);
    }
}
