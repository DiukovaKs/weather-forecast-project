package diukova.test.forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyUnits {
    String time;
    String temperature_2m;
    String relative_humidity_2m;
    String rain;
}
