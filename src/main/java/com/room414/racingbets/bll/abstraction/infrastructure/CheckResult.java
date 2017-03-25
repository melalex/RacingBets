package com.room414.racingbets.bll.abstraction.infrastructure;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public interface CheckResult {
    boolean isEmailExists();
    boolean isLoginExists();
}
