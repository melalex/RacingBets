package com.room414.racingbets.dal.abstraction.factories;

import com.room414.racingbets.dal.abstraction.tokens.ConfirmEmailTokenStorage;
import com.room414.racingbets.dal.abstraction.tokens.RefreshTokenStorage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface TokenStorageFactory {
    ConfirmEmailTokenStorage createConfirmEmailTokenStorage();
    RefreshTokenStorage createRefreshTokenStorage();
}
