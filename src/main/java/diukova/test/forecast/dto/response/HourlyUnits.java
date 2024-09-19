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
public class HourlyUnits implements Serializable {
    String time;
    String temperature_2m;
    String relative_humidity_2m;
    String rain;
}
