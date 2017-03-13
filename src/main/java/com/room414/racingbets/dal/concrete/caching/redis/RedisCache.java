package com.room414.racingbets.dal.concrete.caching.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.dal.abstraction.caching.DaoCache;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.concurrent.Callable;

import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisCache implements DaoCache, AutoCloseable {
    private Jedis jedis;

    public RedisCache(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public <T> T get(String key, Callable<T> getter, Class<T> type) throws Exception {
        String value = jedis.get(key);

        if (value != null) {
            return deserialize(value, type);
        }

        T result = getter.call();

        jedis.set(key, serialize(result));

        return result;
    }

    @Override
    public void delete(String key) {
        jedis.del(key);
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }

    private <T> String serialize(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.writeValueAsString(object);
    }

    private <T> T deserialize(String json, Class<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper.readValue(json, type);
    }
}
