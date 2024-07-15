package diukova.test.forecast.dto.response.emoji;

import lombok.Getter;

@Getter
public enum WindDirectionEmoji {

    NORTH(Character.toString(0x2B07), "N"),
    NORTHEAST(Character.toString(0x2199), "NE"),
    EAST(Character.toString(0x2B05), "E"),
    SOUTHEAST(Character.toString(0x2196), "SE"),
    SOUTH(Character.toString(0x2B06), "S"),
    SOUTHWEST(Character.toString(0x2197), "SW"),
    WEST(Character.toString(0x27A1), "W"),
    NORTHWEST(Character.toString(0x2198), "NW");

    WindDirectionEmoji(String emoji, String abbreviation) {
        this.emoji = emoji;
        this.abbreviation = abbreviation;
    }

    private final String emoji;
    private final String abbreviation;
}
