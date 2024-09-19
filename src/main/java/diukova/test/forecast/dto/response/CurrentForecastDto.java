package diukova.test.forecast.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentForecastDto {
    String[] temperature;
    String[] humidity;
    String[] precipitation;
    String[] weatherCode;
    String[] windSpeed;
    String[] windDirection;
    String[] uvIndex;

    String city;
}
