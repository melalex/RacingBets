package com.room414.racingbets.dal.concrete.caching.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.fasterxml.jackson.annotation.PropertyAccessor.NONE;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisCache implements AutoCloseable {
    Jedis jedis;

    private List<String> toDelete;
    private List<Pair<String, String>> toDeleteFromMap;

    RedisCache(Jedis jedis) {
        this.jedis = jedis;
    }

    List<String> getToDelete() {
        if (toDelete == null) {
            toDelete = new LinkedList<>();
        }
        return toDelete;
    }

    private List<Pair<String, String>> getToDeleteFromMap() {
        if (toDeleteFromMap == null) {
            toDeleteFromMap = new LinkedList<>();
        }
        return toDeleteFromMap;
    }

    private String messageFromPair(Pair<String, String> pair) {
        return pair.getFirstElement() + "@" + pair.getSecondElement();
    }

    public <T> T getCached(String namespace, String key, Getter<T> getter, TypeReference<T> type) {
        try {
            String value = jedis.hget(namespace, key);

            if (value != null) {
                return deserialize(value, type);
            }

            T result = getter.call();

            jedis.hset(namespace, key, serialize(result));

            return result;
        } catch (JsonProcessingException e) {
            String message = "Exception during object -> json serializing";
            throw new DalException(message, e);
        } catch (IOException e) {
            String message = "Exception during json -> object deserialize";
            throw new DalException(message, e);
        }
    }

    public int getCachedCount(String namespace, String key, Getter<Integer> getter) {
        String value = jedis.hget(namespace, key);

        if (value != null) {
            return Integer.valueOf(value);
        }

        Integer result = getter.call();

        jedis.hset(namespace, key, String.valueOf(result));

        return result;
    }

    public void delete(String key) {
        getToDelete().add(key);
    }

    public void delete(String namespace, String key) {
        getToDeleteFromMap().add(new Pair<>(namespace, key));
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


    void commit() throws IOException {
        try (Pipeline pipeline = jedis.pipelined()) {
            if (toDelete != null && !toDelete.isEmpty()) {
                for (String key : toDelete) {
                    pipeline.del(key);
                    pipeline.publish(RedisSubscriber.DELETE_CHANEL_ALL, key);
                }
                toDelete.clear();
            }

            if (toDeleteFromMap != null && !toDeleteFromMap.isEmpty()) {
                for (Pair<String, String> target : toDeleteFromMap) {
                    pipeline.hdel(target.getFirstElement(), target.getSecondElement());
                    pipeline.publish(RedisSubscriber.DELETE_CHANEL_FIELD, messageFromPair(target));
                }
                toDeleteFromMap.clear();
            }
        }
    }

    void rollback() {
        if (toDelete != null) {
            toDelete.clear();
        }

        if (toDeleteFromMap != null) {
            toDeleteFromMap.clear();
        }
    }

    @Override
    public void close() throws IOException {
        jedis.close();
    }
}
