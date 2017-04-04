package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.concrete.infrastrucure.Messenger;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.bll.dto.entities.UserDto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
// TODO: add implementation
public class MessageServiceImpl implements MessageService {
    private Properties properties;

    public MessageServiceImpl(Properties properties) {
        this.properties = properties;
    }

    private Session getSession() {
        return Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                properties.getProperty("username"), properties.getProperty("password")
                        );
                    }
                });
    }

    private void sendMessage(Messenger messenger) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(properties.getProperty("from")));
            messenger.accept(message);
            Transport.send(message);
        } catch (MessagingException e) {
            String message = "Exception during sending mail with properties " + properties;
            throw new BllException(message, e);
        }

    }

    @Override
    public void sendWinMessage(BetDto bet, RaceDto race) {
        sendMessage(message -> {
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bet.getUser().getEmail()));
//            message.setSubject("sendWinMessage");
//            message.setText("Hello, this is sample for to check send email using JavaMailAPI ");
        });
    }

    @Override
    public void sendLoseMessage(BetDto bet, RaceDto race) {
        sendMessage(message -> {
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bet.getUser().getEmail()));
//            message.setSubject("sendLoseMessage");
//            message.setText("Hello, this is sample for to check send email using JavaMailAPI ");
        });
    }

    @Override
    public void sendRejectMessage(BetDto bet, RaceDto race) {
        sendMessage(message -> {
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bet.getUser().getEmail()));
//            message.setSubject("sendRejectMessage");
//            message.setText("Hello, this is sample for to check send email using JavaMailAPI ");
        });
    }

    @Override
    public void sendConfirmMessage(UserDto user, String token) {
        sendMessage(message -> {
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
//            message.setSubject("sendConfirmMessage");
//            message.setText("Confirm email token: " + token);
        });
    }
}
