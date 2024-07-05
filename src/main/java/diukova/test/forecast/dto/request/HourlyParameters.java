package diukova.test.forecast.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyParameters {
    boolean temperature2m;
    boolean relativeHumidity2m;
    boolean precipitation;
    boolean weatherCode;
    boolean windSpeed10m;
    boolean windDirection10m;
    boolean uvIndex;
}
