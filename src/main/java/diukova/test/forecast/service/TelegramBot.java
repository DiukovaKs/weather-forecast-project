package diukova.test.forecast.service;

import diukova.test.forecast.config.BotConfig;
import diukova.test.forecast.dao.CityEntity;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private static final Map<Long, String> CITIES_MAP = new HashMap<>();

    private final BotConfig botConfig;
    private final WeatherForecastService weatherForecastService;
    private final TelegramBotButtonsBuilder buttonsBuilder;

    @Autowired
    private final CityService cityService;

    @PostConstruct
    public void init() {
        List<CityEntity> cities = cityService.getCities();
        cities.forEach(e -> CITIES_MAP.put(e.getId(), e.getCity()));
    }

    @Autowired
    private ChatToCityService service;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String messageText;
        String answer = "";
        long chatId;

        if (isCallBackMessage(update)) {
            messageText = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();

            answer = "The city was saved successfully! " +
                    "Please, type the command to check weather for " +
                    CITIES_MAP.get(Long.parseLong(messageText)) + "!";

            if (messageText.matches("\\d")) {
                service.setCityToChat(chatId, Long.parseLong(messageText));
                sendMessage(chatId, answer);
            } else {
                sendMessage(chatId, "I dont know this command!");
            }

        } else if (update.hasMessage() && update.getMessage().hasText()) {
            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/weather":
                    answer = weatherForecastService.getWeatherMessage(chatId);
                    sendMessage(chatId, answer);
                    break;
                case "/wind":
                    answer = weatherForecastService.getWindForecastMessage(chatId);
                    sendMessage(chatId, answer);
                    break;
                case "/city":
                    setButtons(chatId);
                    break;
                default:
                    sendMessage(chatId, "I dont know this command!");
                    break;
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you! \n" +
                "To get choose city use command   /city \n" +
                "To get weather condition for current day use command   /weather \n" +
                "To get wind information for current day use command   /wind";

        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setParseMode("HTML");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("ERROR " + e.getMessage());
        }
    }

    private void sendMessage(Long chatId, InlineKeyboardMarkup markupInline) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setReplyMarkup(markupInline);
        sendMessage.setText("Choose city");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("ERROR " + e.getMessage());
        }
    }

    private boolean isCallBackMessage(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getMessage().hasText();
    }

    private void setButtons(Long chatId) {
         InlineKeyboardMarkup markupInline = buttonsBuilder.createButtons(CITIES_MAP);

        sendMessage(chatId, markupInline);
    }
}
