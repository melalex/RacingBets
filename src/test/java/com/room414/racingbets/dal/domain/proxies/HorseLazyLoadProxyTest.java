package com.room414.racingbets.dal.domain.proxies;

import org.junit.jupiter.api.Test;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
class HorseLazyLoadProxyTest {
    @Test
    void create_zeroId_returnedNull() {
        HorseLazyLoadProxy proxy = HorseLazyLoadProxy.create(0);

        assert proxy == null : "proxy != null";
    }
}