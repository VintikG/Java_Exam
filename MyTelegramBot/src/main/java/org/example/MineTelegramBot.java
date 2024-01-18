package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.vdurmont.emoji.EmojiParser;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MineTelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();
        String user_first_name = update.getMessage().getChat().getFirstName();
        String user_last_name = update.getMessage().getChat().getLastName();
        String user_username = update.getMessage().getChat().getUserName();
        long user_id = update.getMessage().getChat().getId();

        log(user_first_name, user_last_name, Long.toString(user_id), message_text);


        // ----if message hasText()----
        if (update.hasMessage() && update.getMessage().hasText()) {

            // ----Command /pic send photo----
            if (message_text.equals("/start")) {
                SendMessage response = new SendMessage();
                response.setChatId(chat_id);
                String answer = EmojiParser.parseToUnicode("Welcome to the chat, buddy \uD83D\uDE0E");
                response.setText(answer);

                try {
                    execute(response);
                } catch (TelegramApiException E) {
                    E.printStackTrace();
                }

            }
            else if (message_text.equals("Photo 1")) {
                SendPhoto response = new SendPhoto();
                response.setChatId(chat_id);
                response.setPhoto(new InputFile("https://sun6-21.userapi.com/s/v1/ig2/T8mWmJ_YzRa-bJqs2zZsVQnPgVR5s1AK94EPnCa_m7dwrB81ve2DWWyUz-pWDCXIAuTBbhq-WURreWQ4p7v0omB6.jpg?size=785x785&quality=96&crop=133,1,785,785&ava=1"));
                String answer = EmojiParser.parseToUnicode("\uD83E\uDD2B");
                response.setCaption(answer);

                try {
                    execute(response);
                } catch (TelegramApiException E) {
                    E.printStackTrace();
                }

            }
            else if (message_text.equals("Photo 2")) {
                SendPhoto response = new SendPhoto();
                response.setChatId(chat_id);
                response.setPhoto(new InputFile("https://media.tenor.com/rSyiXtfSQokAAAAM/cat-smug-face.gif"));
                String answer = EmojiParser.parseToUnicode("\uD83D\uDE0F");
                response.setCaption(answer);


                try {
                    execute(response);
                } catch (TelegramApiException E) {
                    E.printStackTrace();
                }


            }
            else if (message_text.equals("Photo 3")) {
                SendPhoto response = new SendPhoto();
                response.setChatId(chat_id);
                response.setPhoto(new InputFile("https://i.ytimg.com/vi/CmkgMs4U5-8/maxresdefault.jpg"));
                String answer = EmojiParser.parseToUnicode("\uD83E\uDD25");
                response.setCaption(answer);


                try {
                    execute(response);
                } catch (TelegramApiException E) {
                    E.printStackTrace();
                }
            }






                // ----creating keyboard with 2 row and 6 button----
            else if (message_text.equals("/markup")) {
                SendMessage response = new SendMessage();
                response.setChatId(chat_id);
                response.setText("Keyboard:");

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

                List<KeyboardRow> keyboard = new ArrayList<>();

                KeyboardRow row = new KeyboardRow();
                row.add("Photo 1");
                row.add("Photo 2");
                row.add("Photo 3");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                response.setReplyMarkup((keyboardMarkup));
                keyboardMarkup.setResizeKeyboard(true);
                try {
                    execute(response);
                } catch (TelegramApiException E) {
                    E.printStackTrace();
                }
            }


            // ----response of copy message----
            else {
                SendMessage response = new SendMessage();
                response.setChatId(chat_id);
                String answer = EmojiParser.parseToUnicode("Unknown command \uD83D\uDDFF Try again");
                response.setText(answer);

                try {
                    execute(response);
                } catch (TelegramApiException E) {
                    E.printStackTrace();
                }

            }

        }

        // ----Send photo caption----
        else if (update.hasMessage() && update.getMessage().hasPhoto()) {


            // Array with photo objects with different sizes
            // We will get the biggest photo from that array
            List<PhotoSize> photos = update.getMessage().getPhoto();

            String f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();

            int f_width = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();

            int f_height = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();

            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width)
                    + "\nheight: " + Integer.toString(f_height);

            SendPhoto response = new SendPhoto();
            response.setChatId(chat_id);
            response.setPhoto(new InputFile(f_id));
            response.setCaption(caption);

            try {
                execute(response);
            } catch (TelegramApiException E) {
                E.printStackTrace();
            }
        }

    }


    // ---- Logs of username----
    private void log(String first_name, String last_name, String user_id, String txt) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name +
                ". (id = " + user_id + ") \n Text - " + txt);
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "VikingTelegramBot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "6360786855:AAGY-PxioCr2z7-Xg4M4q7djUA0VvEpuwAs";
    }
}