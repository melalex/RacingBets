package com.room414.racingbets.web.model.exceptions;

/**
 * @author Alexander Melashchenko
 * @version 1.0 04 Apr 2017
 */
public class WebException extends RuntimeException {
    private static final long serialVersionUID = 8760636591315901110L;

    public WebException(String message) {
        super(message);
    }

    public WebException(Throwable cause) {
        super(cause);
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }
}
