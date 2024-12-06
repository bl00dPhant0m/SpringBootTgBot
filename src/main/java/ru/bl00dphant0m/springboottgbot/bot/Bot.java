package ru.bl00dphant0m.springboottgbot.bot;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bl00dphant0m.springboottgbot.service.MessageService;


@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${telegram.username}")
    private String username;

    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    public Bot(@Value("${telegram.token}") String token, @Autowired MessageService messageService) {
        super(token);
        this.messageService = messageService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String textFromUser = update.getMessage().getText();


        logger.info("Bot: get message {} from {}", textFromUser, chatId.toString());
        String textToUser = messageService.buildMessage(textFromUser);

        sendMessage(chatId, textToUser);
    }

    private void  sendMessage(Long chatId,String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        try {
            execute(sendMessage);
            logger.info("Bot: {} send message: {}", chatId, text);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return username;
    }
}
