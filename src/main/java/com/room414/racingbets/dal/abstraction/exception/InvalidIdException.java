package com.room414.racingbets.dal.abstraction.exception;

/**
 * @author Alexander Melashchenko
 * @version 1.0 09 Apr 2017
 */
public class InvalidIdException extends RuntimeException {
    private static final long serialVersionUID = 863962108363490728L;

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(Throwable cause) {
        super(cause);
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
