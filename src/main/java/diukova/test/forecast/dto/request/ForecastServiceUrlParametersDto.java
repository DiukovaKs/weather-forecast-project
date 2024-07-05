package diukova.test.forecast.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastServiceUrlParametersDto {
    String longitude;
    String latitude;
    HourlyParameters hourlyParameters;
    String windSpeedUnit;
    String timezone;
    int forecastDays;
}
