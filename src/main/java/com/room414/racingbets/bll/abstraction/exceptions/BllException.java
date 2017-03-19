package com.room414.racingbets.bll.abstraction.exceptions;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class BllException extends RuntimeException {
    private static final long serialVersionUID = 8760636591315901110L;

    public BllException(String message) {
        super(message);
    }

    public BllException(Throwable cause) {
        super(cause);
    }

    public BllException(String message, Throwable cause) {
        super(message, cause);
    }
}
