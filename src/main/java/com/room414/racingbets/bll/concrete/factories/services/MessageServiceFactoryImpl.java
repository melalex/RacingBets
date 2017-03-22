package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.MessageServiceFactory;
import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.concrete.services.MessageServiceImpl;

import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class MessageServiceFactoryImpl implements MessageServiceFactory {
    private MessageService messageService;

    MessageServiceFactoryImpl(Properties properties) {
        messageService = new MessageServiceImpl(properties);
    }

    @Override
    public MessageService getMessageService() {
        return messageService;
    }
}
