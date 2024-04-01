package com.example.bottg.commands;

import com.example.bottg.helpers.DoctorHelper;
import com.example.bottg.helpers.TimeControl;
import com.example.bottg.helpers.UserHelper;
import com.example.bottg.models.BookModel;
import com.example.bottg.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.List;

@Component

public class ChooseTime implements WorkerCommand {
    @Override
    public SendMessage start(Update update) {
        TimeControl timeControl = new TimeControl();
        List<String> list = timeControl.getTimes();
        boolean ifThisCommand=false;
        for (String str: list){
            if (update.getMessage().getText().equals(str)){
                ifThisCommand = true;
            }
        }
        if(!ifThisCommand){
            return null;
        }
        BookModel bookModel = new BookModel();
        bookModel.setTime(update.getMessage().getText().toString());

        UserModel userModel = new UserModel();
        userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());

        bookModel.setTgId(update.getMessage().getFrom().getId().toString());
        bookModel.setDoctorEnum(userModel.getDoctorEnum());

        DoctorHelper.save(bookModel);

        System.out.println("Запись к врачу");
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardRemove rmkb = new ReplyKeyboardRemove(true);
        rmkb.setSelective(true);
        sendMessage.setReplyMarkup(rmkb);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Вы успешно записались к врачу");
        return sendMessage;


    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
