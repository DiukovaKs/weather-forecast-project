package diukova.test.forecast.service;

import diukova.test.forecast.dto.request.ForecastServiceUrlParametersDto;
import diukova.test.forecast.dto.request.HourlyParameters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ForecastUrlBuilder {
    private static final String URL = "https://api.open-meteo.com/v1/forecast?";

    public String createForecastUrl(ForecastServiceUrlParametersDto parametersDto) {
        String result;

        StringBuilder builder = new StringBuilder();
        builder.append(URL)
                .append("latitude=")
                .append(parametersDto.getLatitude())
                .append("&longitude=")
                .append(parametersDto.getLongitude())
                .append("&hourly=");

        if (parametersDto.getHourlyParameters().isTemperature2m()) {
            builder.append("temperature_2m,");
        }
        if (parametersDto.getHourlyParameters().isRelativeHumidity2m()) {
            builder.append("relative_humidity_2m,");
        }
        if (parametersDto.getHourlyParameters().isPrecipitation()) {
            builder.append("precipitation,");
        }
        if (parametersDto.getHourlyParameters().isWeatherCode()) {
            builder.append("weather_code,");
        }
        if (parametersDto.getHourlyParameters().isWindSpeed10m()) {
            builder.append("wind_speed_10m,");
        }
        if (parametersDto.getHourlyParameters().isWindDirection10m()) {
            builder.append("wind_direction_10m,");
        }
        if (parametersDto.getHourlyParameters().isUvIndex()) {
            builder.append("uv_index");
        }

        builder.append("&wind_speed_unit=")
                .append(parametersDto.getWindSpeedUnit())
                .append("&timezone=")
                .append(parametersDto.getTimezone())
                .append("&forecast_days=")
                .append(parametersDto.getForecastDays());

        result = builder.toString();

        return result;
    }

    public String getDefaultURL() {
        return createForecastUrl(getDefaultParameters());
    }

    private ForecastServiceUrlParametersDto getDefaultParameters() {
        ForecastServiceUrlParametersDto parametersDto = new ForecastServiceUrlParametersDto();
        parametersDto.setLongitude("174.7756");
        parametersDto.setLatitude("-41.2866");
        parametersDto.setWindSpeedUnit("ms");
        parametersDto.setTimezone("Pacific/Auckland");
        parametersDto.setForecastDays(1);

        HourlyParameters hourlyParameters = new HourlyParameters();
        hourlyParameters.setTemperature2m(true);
        hourlyParameters.setRelativeHumidity2m(true);
        hourlyParameters.setPrecipitation(true);
        hourlyParameters.setWeatherCode(true);
        hourlyParameters.setWindSpeed10m(true);
        hourlyParameters.setWindDirection10m(true);
        hourlyParameters.setUvIndex(true);

        parametersDto.setHourlyParameters(hourlyParameters);

        return parametersDto;
    }

}
