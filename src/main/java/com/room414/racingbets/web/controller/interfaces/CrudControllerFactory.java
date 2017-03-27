package com.room414.racingbets.web.controller.interfaces;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public interface CrudControllerFactory {
    CrudController create(Locale locale);
}
