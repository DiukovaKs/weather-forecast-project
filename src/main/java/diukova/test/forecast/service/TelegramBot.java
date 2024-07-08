package diukova.test.forecast.service;

import diukova.test.forecast.config.BotConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final WeatherForecastService weatherForecastService;

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
            String buttonText = update.getCallbackQuery().getMessage().getText();
            System.out.println("Message button text: " + buttonText);
            chatId = update.getCallbackQuery().getMessage().getChatId();

            answer = "The city was saved successfully! Please, type the command!";

            switch (messageText){
                case "/Wellington":
                    service.setCityToChat(chatId, 1L);
                    sendMessage(chatId, answer);
                    break;
                case "/Auckland":
                    service.setCityToChat(chatId, 2L);
                    sendMessage(chatId, answer);
                    break;
                case "/Rotorua":
                    service.setCityToChat(chatId, 3L);
                    sendMessage(chatId, answer);
                    break;
                case "/Petone":
                    service.setCityToChat(chatId, 4L);
                    sendMessage(chatId, answer);
                    break;
                case "/Lower Hutt":
                    service.setCityToChat(chatId, 5L);
                    sendMessage(chatId, answer);
                    break;
                case "/Upper Hutt":
                    service.setCityToChat(chatId, 6L);
                    sendMessage(chatId, answer);
                    break;
                case "/Christchurch":
                    service.setCityToChat(chatId, 8L);
                    sendMessage(chatId, answer);
                    break;
                case "/Novosibirsk":
                    service.setCityToChat(chatId, 7L);
                    sendMessage(chatId, answer);
                    break;
                default:
                    sendMessage(chatId, "I dont know this command!");
                    break;
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
                    sendMessage(chatId, "I dont know this command!!");
                    break;
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you! \n" +
                "To get weather condition for current day use command   /weather \n" +
                "To get wind information for current day use command   /wind";

        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

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
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline4 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Wellington");
        button1.setCallbackData("/Wellington");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Auckland");
        button2.setCallbackData("/Auckland");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Rotorua");
        button3.setCallbackData("/Rotorua");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Petone");
        button4.setCallbackData("/Petone");

        InlineKeyboardButton button5 = new InlineKeyboardButton();
        button5.setText("Lower Hutt");
        button5.setCallbackData("/Lower Hutt");

        InlineKeyboardButton button6 = new InlineKeyboardButton();
        button6.setText("Upper Hutt");
        button6.setCallbackData("/Upper Hutt");

        InlineKeyboardButton button7 = new InlineKeyboardButton();
        button7.setText("Christchurch");
        button7.setCallbackData("/Christchurch");

        InlineKeyboardButton button8 = new InlineKeyboardButton();
        button8.setText("Novosibirsk");
        button8.setCallbackData("/Novosibirsk");

        rowInline1.add(button1);
        rowInline1.add(button2);
        rowInline2.add(button3);
        rowInline2.add(button4);
        rowInline3.add(button5);
        rowInline3.add(button6);
        rowInline4.add(button7);
        rowInline4.add(button8);

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);

        markupInline.setKeyboard(rowsInline);

        sendMessage(chatId, markupInline);
    }
}
