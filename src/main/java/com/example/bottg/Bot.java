package com.example.bottg;

import com.example.bottg.commands.BookCommand;
import com.example.bottg.commands.ChooseTime;
import com.example.bottg.commands.LoginCommand;
import com.example.bottg.commands.WorkerCommand;
import com.example.bottg.commands.bookcommand.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Bot  extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "spring_docttor_bot";
    }
    @Override
    public String getBotToken(){
        return "6545369520:AAHkiXm7zXyPkal4Z49nl51C6eiXNRbLZ8M";
    }

    @Override
    public void onUpdateReceived(Update update) {
        KeyboardRow k1 = new KeyboardRow();
        k1.add(new KeyboardButton("Log In"));
        k1.add(new KeyboardButton("Записаться к врачу"));
        KeyboardRow k2 = new KeyboardRow();
        k2.add(new KeyboardButton("Отменить запись к врачу"));
        List<KeyboardRow> listK = new ArrayList<>();
        listK.add(k1);
        listK.add(k2);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите действие");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(listK);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<WorkerCommand> list = new ArrayList<>();
        list.add(new LoginCommand());
        list.add(new BookCommand());
        list.add(new TerapevtBookCommand());
        list.add(new AllergologBookCommand());
        list.add(new GinekologBookCommand());
        list.add(new HirurgBookCommand());
        list.add(new LorBookCommand());
        list.add(new OkulistBookCommand());
        list.add(new ChooseTime());

        for (WorkerCommand w: list){
            SendMessage res = w.start(update);
            if (res!= null){
                sendMessage = res;
                break;
            }
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
