package diukova.test.forecast.mapper;

import diukova.test.forecast.dto.response.CurrentForecastDto;
import diukova.test.forecast.dto.response.emoji.WeatherCodeEmoji;
import diukova.test.forecast.dto.response.WeatherForecastDto;
import diukova.test.forecast.dto.response.emoji.WindDirectionEmoji;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class DtoMapper {

    @Mapping(target = "temperature", source = "hourly.temperature_2m")
    @Mapping(target = "humidity", source = "hourly.relative_humidity_2m")
    @Mapping(target = "precipitation", source = "hourly.precipitation")
    @Mapping(target = "weatherCode", source = "hourly.weather_code")
    @Mapping(target = "windSpeed", source = "hourly.wind_speed_10m")
    @Mapping(target = "windDirection", source = "hourly.wind_direction_10m")
    @Mapping(target = "uvIndex", source = "hourly.uv_index")
    public abstract CurrentForecastDto toCurrentForecastDto(WeatherForecastDto weatherForecastDto);

    @AfterMapping
    protected void postMapping(@MappingTarget CurrentForecastDto dto, WeatherForecastDto quizDto) {
        String[] temperature = getFourValues(dto.getTemperature());
        String[] humidity = getFourValues(dto.getHumidity());
        String[] precipitation = getFourValues(dto.getPrecipitation());
        String[] weatherCode = getFourValues(dto.getWeatherCode());
        String[] windSpeed = getFourValues(dto.getWindSpeed());
        String[] windDirection = getFourValues(dto.getWindDirection());
        String[] uvIndex = getFourValues(dto.getUvIndex());

        dto.setTemperature(temperature);
        dto.setHumidity(humidity);
        dto.setPrecipitation(precipitation);
        dto.setWeatherCode(getWeatherCodeEmoji(weatherCode));
        dto.setWindSpeed(windSpeed);
        dto.setWindDirection(getWindDirection(windDirection));
        dto.setUvIndex(uvIndex);
    }

    private String[] getFourValues(Object[] arr) {
        if (arr == null) {
            return getEmptyResultArray();
        }

        return new String[]{arr[7].toString(), arr[11].toString(), arr[15].toString(), arr[19].toString()};
    }

    private String[] getWindDirection(String[] windDirection) {
        if (windDirection == null) {
            return getEmptyResultArray();
        }

        String[] result = new String[windDirection.length];

        for (int i = 0; i < windDirection.length; i++) {
            int degree = Integer.parseInt(windDirection[i]);

            if (degree < 90 || degree > 270) {
                if (degree < 22 || degree > 338) {
                    result[i] = WindDirectionEmoji.NORTH.getEmoji() + " " + WindDirectionEmoji.NORTH.getAbbreviation();
                } else if ((degree == 22 || degree > 22) && degree < 90) {
                    result[i] = WindDirectionEmoji.NORTHEAST.getEmoji() + " " + WindDirectionEmoji.NORTHEAST.getAbbreviation();
                } else if ((degree == 338 || degree < 338 ) && degree > 270) {
                    result[i] = WindDirectionEmoji.NORTHWEST.getEmoji() + " " + WindDirectionEmoji.NORTHWEST.getAbbreviation();
                }
            } else if (degree > 90 && degree < 270) {
                if (degree > 158 && degree < 202) {
                    result[i] = WindDirectionEmoji.SOUTH.getEmoji() + " " + WindDirectionEmoji.SOUTH.getAbbreviation();
                } else if (degree == 158 || degree < 158) {
                    result[i] = WindDirectionEmoji.SOUTHEAST.getEmoji() + " " + WindDirectionEmoji.SOUTHEAST.getAbbreviation();
                } else if (degree == 202 || degree > 202) {
                    result[i] = WindDirectionEmoji.SOUTHWEST.getEmoji() + " " + WindDirectionEmoji.SOUTHWEST.getAbbreviation();
                }
            } else if (degree == 90) {
                result[i] = WindDirectionEmoji.EAST.getEmoji() + " " + WindDirectionEmoji.EAST.getAbbreviation();
            } else {
                result[i] = WindDirectionEmoji.WEST.getEmoji() + " " + WindDirectionEmoji.WEST.getAbbreviation();
            }
        }

        return result;
    }

    private String[] getWeatherCodeEmoji(String[] weatherCode) {
        if (weatherCode == null) {
            return getEmptyResultArray();
        }

        String[] result = new String[weatherCode.length];

        for (int i = 0; i < weatherCode.length; i++) {
            String code = weatherCode[i];

            List<WeatherCodeEmoji> codes = Arrays.stream(WeatherCodeEmoji.values())
                    .filter(e -> e.getCode().contains(code))
                    .toList();

            result[i] = Optional.of(codes.get(0).getEmoji()).orElse("");
        }

        return result;
    }

    private String[] getEmptyResultArray() {
        return new String[]{"N/A", "N/A", "N/A", "N/A"};
    }
}
