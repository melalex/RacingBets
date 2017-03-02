package com.room414.racingbets.dal.abstraction.exception;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class DalException extends Exception {
    private static final long serialVersionUID = -8842582617099052727L;

    public DalException(String message) {
        super(message);
    }

    public DalException(Throwable cause) {
        super(cause);
    }

    public DalException(String message, Throwable cause) {
        super(message, cause);
    }
}
