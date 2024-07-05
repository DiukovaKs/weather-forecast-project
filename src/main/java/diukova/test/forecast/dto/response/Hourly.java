package diukova.test.forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hourly {
    String[] time;
    float[] temperature_2m;
    int[] relative_humidity_2m;
    int[] precipitation;
    int[] weather_code;
    float[] wind_speed_10m;
    int[] wind_direction_10m;
    float[] uv_index;
}
