package com.room414.racingbets.bll.concrete.infrastrucure;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
@FunctionalInterface
public interface Messenger {
    void accept(Message message) throws MessagingException;
}
