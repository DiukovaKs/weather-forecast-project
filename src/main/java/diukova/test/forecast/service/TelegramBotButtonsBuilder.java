package diukova.test.forecast.service;

import diukova.test.forecast.dao.CityEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TelegramBotButtonsBuilder {

    public InlineKeyboardMarkup createButtons(Map<Long, String> citiesMap) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<CityEntity> cities = citiesMap.entrySet()
                .stream()
                .map(e -> {
                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setId(e.getKey());
                    cityEntity.setCity(e.getValue());

                    return cityEntity;
                }).toList();
        int k = 0;

        for (int i = 0; i < cities.size()/2; i++) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            for (int j = 0; j < 2; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(cities.get(k+j).getCity());
                button.setCallbackData(cities.get(k+j).getId().toString());

                rowInline.add(button);
            }

            rowsInline.add(rowInline);
            k = k + 2;
        }

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        return inlineKeyboardMarkup;
    }
}
