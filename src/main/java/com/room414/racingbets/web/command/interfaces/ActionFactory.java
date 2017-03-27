package com.room414.racingbets.web.command.interfaces;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public interface ActionFactory {
    Action create(String name, Locale locale);
}
