package com.room414.racingbets.dal.concrete.caching.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisCache implements Closeable {
    protected Jedis jedis;

    public RedisCache(Jedis jedis) {
        this.jedis = jedis;
    }

    public <T> T getOneCached(String key, Callable<T> getter, TypeReference<T> type) throws Exception {
        String value = jedis.get(key);

        if (value != null) {
            return deserialize(value, type);
        }

        T result = getter.call();

        jedis.set(key, serialize(result));

        return result;
    }

    public <T> List<T> getManyCached(
            String namespace, String key, Callable<List<T>> getter, TypeReference<List<T>> type
    ) throws Exception {

        String value = jedis.hget(namespace, key);

        if (value != null) {
            return deserialize(value, type);
        }

        List<T> result = getter.call();

        jedis.set(key, serialize(result));

        return result;
    }

    public void delete(String key) {
        jedis.del(key);
    }

    @Override
    public void close() {
        jedis.close();
    }

    private <T> String serialize(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.writeValueAsString(object);
    }

    private <T> T deserialize(String json, TypeReference<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper.readValue(json, type);
    }
}
