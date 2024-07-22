package diukova.test.forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class WeatherForecastDto implements Serializable {
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
