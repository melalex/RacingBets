package com.room414.racingbets.dal.concrete.caching.caffeine.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
class BaseCacheTest {
    private CachePool<Pair<String, Integer>> cachePool;

    private BaseCache<Pair<String, Integer>> createBaseCache() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<CachePool> constructor = CachePool.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        cachePool = constructor.newInstance();

        RedisCache redisCache = mock(RedisCache.class);

        when(redisCache.getCached(anyString(), anyString(), any(), any()))
                .then(invocationOnMock -> ((Getter) invocationOnMock.getArguments()[2]).call());

        when(redisCache.getCachedCount(anyString(), anyString(), any()))
                .then(invocationOnMock -> ((Getter) invocationOnMock.getArguments()[2]).call());

        return new BaseCache<Pair<String, Integer>>(,
                "",
                new TypeReference<Pair<String, Integer>>() {},
                new TypeReference<List<Pair<String, Integer>>>() {},
                redisCache) { };
    }

    @Test
    void getOneCached() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BaseCache<Pair<String, Integer>> baseCache = createBaseCache();

        String key = "get:cached:" + System.currentTimeMillis();
        Pair<String, Integer> target = new Pair<>("Cache", 1);

        Pair<String, Integer> result = baseCache.getOneCached(key, () -> target);

        assert target.equals(result) : defaultAssertionFailMessage(result, target);

        result = cachePool.getCache().get(key, k -> null);

        assert target.equals(result) : defaultAssertionFailMessage(result, target);
    }

    @Test
    void getManyCached() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BaseCache<Pair<String, Integer>> baseCache = createBaseCache();

        String key = "get:cached:" + System.currentTimeMillis();
        List<Pair<String, Integer>> target = new LinkedList<>();
        target.add(new Pair<>("Cache", 1));
        target.add(new Pair<>("Cache", 1));
        target.add(new Pair<>("Cache", 1));

        List<Pair<String, Integer>> result = baseCache.getManyCached(key, () -> target);

        assert target.equals(result) : defaultAssertionFailMessage(result, target);

        result = cachePool.getListCache().get(key, k -> null);

        assert target.equals(result) : defaultAssertionFailMessage(result, target);
    }

    @Test
    void getCachedCount() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BaseCache<Pair<String, Integer>> baseCache = createBaseCache();

        long target = System.currentTimeMillis();
        String key = "get:cached:count:" + target;

        long result = baseCache.getCachedCount(key, () -> target);

        assert target == result : defaultAssertionFailMessage(result, target);

        result = cachePool.getCountCache().get(key, k -> (long) -1);

        assert target == result : defaultAssertionFailMessage(result, target);
    }

    @Test
    void deleteOneCached() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BaseCache<Pair<String, Integer>> baseCache = createBaseCache();

        String key = "get:cached:" + System.currentTimeMillis();
        Pair<String, Integer> target = new Pair<>("Cache", 1);

        baseCache.getOneCached(key, () -> target);

        baseCache.deleteOneCached(key);

        assert cachePool.getCache().getIfPresent(key) == null : "Cache didn't be invalidate";
    }

    @Test
    void deleteAllCached() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BaseCache<Pair<String, Integer>> baseCache = createBaseCache();

        long targetCount = System.currentTimeMillis();

        String key = "get:cached:" + targetCount;

        Pair<String, Integer> target = new Pair<>("Cache", 1);

        List<Pair<String, Integer>> targetList = new LinkedList<>();
        targetList.add(new Pair<>("Cache", 1));
        targetList.add(new Pair<>("Cache", 1));
        targetList.add(new Pair<>("Cache", 1));

        baseCache.getOneCached(key, () -> target);
        baseCache.getManyCached(key, () -> targetList);
        baseCache.getCachedCount(key, () -> targetCount);

        baseCache.deleteAllCached();

        assert cachePool.getCache().asMap().isEmpty() : "Cache didn't be invalidate";
        assert cachePool.getListCache().asMap().isEmpty() : "List Cache didn't be invalidate";
        assert cachePool.getCountCache().asMap().isEmpty() : "Count Cache didn't be invalidate";
    }

    @Test
    void deleteManyCached() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BaseCache<Pair<String, Integer>> baseCache = createBaseCache();

        String key = "get:cached:" + System.currentTimeMillis();

        List<Pair<String, Integer>> targetList1 = new LinkedList<>();
        targetList1.add(new Pair<>("Cache", 1));
        targetList1.add(new Pair<>("Cache", 1));
        targetList1.add(new Pair<>("Cache", 1));

        List<Pair<String, Integer>> targetList2 = new LinkedList<>();
        targetList2.add(new Pair<>("Cache", 1));
        targetList2.add(new Pair<>("Cache", 1));
        targetList2.add(new Pair<>("Cache", 1));


        baseCache.getManyCached(key, () -> targetList1);
        baseCache.getManyCached(key, () -> targetList2);

        baseCache.deleteManyCached();

        assert cachePool.getListCache().asMap().isEmpty() : "List Cache didn't be invalidate";
    }
}