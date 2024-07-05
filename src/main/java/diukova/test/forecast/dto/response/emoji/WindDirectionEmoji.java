package diukova.test.forecast.dto.response.emoji;

import lombok.Getter;

@Getter
public enum WindDirectionEmoji {

    NORTH("↓", "N"),
    NORTHEAST("↙", "NE"),
    EAST("←", "E"),
    SOUTHEAST("↖", "SE"),
    SOUTH("↑", "S"),
    SOUTHWEST("↗", "SW"),
    WEST("→", "W"),
    NORTHWEST("↘", "NW");

    WindDirectionEmoji(String emoji, String abbreviation) {
        this.emoji = emoji;
        this.abbreviation = abbreviation;
    }

    private final String emoji;
    private final String abbreviation;
}
