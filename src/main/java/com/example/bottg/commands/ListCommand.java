package com.example.bottg.commands;

import com.example.bottg.helpers.DoctorHelper;
import com.example.bottg.helpers.UserHelper;
import com.example.bottg.models.BookModel;
import com.example.bottg.models.UserModel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component

public class CancelCommand implements WorkerCommand{
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Список твоей записи")) {
            return null;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Список и время");
        List<BookModel> list = DoctorHelper.listDoctors(update.getMessage().getFrom().getId().toString());
        System.out.println(list);

        KeyboardRow keyboardRow = new KeyboardRow();

        for (BookModel b : list) {

            keyboardRow.add(new KeyboardButton(b.getDoctorEnum().toString() + " " + b.getTime()));
        }

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
