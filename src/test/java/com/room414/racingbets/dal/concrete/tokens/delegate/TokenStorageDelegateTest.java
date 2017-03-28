package com.room414.racingbets.dal.concrete.tokens.delegate;

import com.room414.racingbets.dal.infrastructure.factories.TestingRedisFactory;
import com.room414.racingbets.resolvers.RedisParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import redis.clients.jedis.Jedis;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
@ExtendWith(RedisParameterResolver.class)
class TokenStorageDelegateTest {
    private static TestingRedisFactory factory;

    @BeforeAll
    static void setUp(TestingRedisFactory factory) {
        TokenStorageDelegateTest.factory = factory;
    }

    @AfterAll
    static void tearDown() throws Exception {
        try(Jedis jedis = factory.getConnection()) {
            jedis.flushDB();
        }
        factory.close();
    }

    @Test
    void createToken_getIdByToken() {
        long id = 1;
        int expire = 100;
        Jedis jedis = factory.getConnection();
        String namespace = "test";

        TokenStorageDelegate storageDelegate = new TokenStorageDelegate(jedis, namespace, expire);
        String token = storageDelegate.createToken(id);
        long result = storageDelegate.getIdByToken(token);

        assert id == result : defaultAssertionFailMessage(result, id);
    }

    @Test
    void getToken_absent_return0() {
        int expire = 100;
        Jedis jedis = factory.getConnection();
        String namespace = "test";

        TokenStorageDelegate storageDelegate = new TokenStorageDelegate(jedis, namespace, expire);
        long result = storageDelegate.getIdByToken("bla-bla-bla");

        assert 0 == result : defaultAssertionFailMessage(result, 0);
    }

    @Test
    void createToken_getIdByToken_expire() throws InterruptedException {
        long id = 2;
        int expire = 5;
        Jedis jedis = factory.getConnection();
        String namespace = "test";

        TokenStorageDelegate storageDelegate = new TokenStorageDelegate(jedis, namespace, expire);
        String token = storageDelegate.createToken(id);

        Thread.sleep(6_000);

        long result = storageDelegate.getIdByToken(token);

        assert 0 == result : defaultAssertionFailMessage(result, 0);
    }
}