package com.room414.racingbets.dal.abstraction.tokens;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface ConfirmEmailTokenStorage extends AutoCloseable {
    String createToken(long id);
    long getIdByToken(String refreshToken);

    @Override
    void close();
}
