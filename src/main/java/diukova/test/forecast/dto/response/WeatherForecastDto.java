package diukova.test.forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastDto {
    Float longitude;
    Float latitude;
    Float elevation;
    Float generationtime_ms;
    Integer utc_offset_seconds;
    String timezone;
    Hourly hourly;
    HourlyUnits hourly_units;

    String city;
}
