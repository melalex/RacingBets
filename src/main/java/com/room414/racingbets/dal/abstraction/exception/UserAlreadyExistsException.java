package com.room414.racingbets.dal.abstraction.exception;

/**
 * Raised while trying add user with login that already exists
 * 
 * @author Alexander Melashchenko
 * @version 1.0 17 Mar 2017
 */
public class UserAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 6435063978209350196L;

    private boolean emailExists;
    private boolean loginExists;

    public UserAlreadyExistsException(String message, boolean emailExists, boolean loginExists) {
        super(message);
        this.emailExists = emailExists;
        this.loginExists = loginExists;
    }

    public UserAlreadyExistsException(Throwable cause, boolean emailExists, boolean loginExists) {
        super(cause);
        this.emailExists = emailExists;
        this.loginExists = loginExists;
    }

    public UserAlreadyExistsException(String message, Throwable cause, boolean emailExists, boolean loginExists) {
        super(message, cause);
        this.emailExists = emailExists;
        this.loginExists = loginExists;
    }

    public boolean isEmailExists() {
        return emailExists;
    }

    public boolean isLoginExists() {
        return loginExists;
    }
}
