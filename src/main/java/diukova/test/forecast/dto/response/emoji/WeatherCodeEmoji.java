package diukova.test.forecast.dto.response.emoji;

import lombok.Getter;

@Getter
public enum WeatherCodeEmoji {
    SUNNY("0", "☀\uFE0F", "Clear sky"),
    PARTLY_CLOUDY("1,2,3", "\uD83C\uDF24", "Mainly clear, partly cloudy, and overcast"),
    FOG("45,48", "\uD83C\uDF2B", "Fog and depositing rime fog"),
    DRIZZLE("51,53,55", "\uD83C\uDF26", "Drizzle: Light, moderate, and dense intensity"),
    FREEZING_DRIZZLE("56,57", "\uD83C\uDF26", "Freezing Drizzle: Light and dense intensity"),
    RAIN("61,63,65", "\uD83C\uDF27", "Rain: Slight, moderate and heavy intensity"),
    FREEZING_RAIN("66,67", "\uD83C\uDF27", "Freezing Rain: Light and heavy intensity"),
    SNOW("71,73,75", "\uD83C\uDF28", "Snow fall: Slight, moderate, and heavy intensity"),
    SNOW_GRAINS("77", "\uD83C\uDF28", "Snow grains"),
    RAIN_SHOWERS("80,81,82", "\uD83C\uDF27", "Rain showers: Slight, moderate, and violent"),
    SNOW_SHOWERS("85, 86", "\uD83C\uDF28", "Snow showers slight and heavy"),
    THUNDERSTORM("95", "⛈", "Thunderstorm: Slight or moderate"),
    THUNDERSTORM_WITH_SLIGHT("96,99", "⛈", "Thunderstorm with slight and heavy hail")
    ;

    WeatherCodeEmoji(String code, String emoji, String description) {
        this.code = code;
        this.emoji = emoji;
        this.description = description;
    }

    private final String code;
    private final String emoji;
    private final String description;
}
