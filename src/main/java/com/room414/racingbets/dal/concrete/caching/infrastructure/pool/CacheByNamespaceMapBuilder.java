package com.room414.racingbets.dal.concrete.caching.infrastructure.pool;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Melashchenko
 * @version 1.0 17 Mar 2017
 */
class CacheByNamespaceMapBuilder {
    private List<Map<String, Cache>> toBuild = new LinkedList<>();

    private CacheByNamespaceMapBuilder() {

    }

    static CacheByNamespaceMapBuilder builder() {
        return new CacheByNamespaceMapBuilder();
    }

    CacheByNamespaceMapBuilder addMap(Map<String, Cache> map) {
        toBuild.add(map);
        return this;
    }

    // TODO: Refactor with streams
    Map<String, Cache> build() {
        Map<String, Cache> result = new HashMap<>();

        for (Map<String, Cache> stringCacheMap : toBuild) {
            result.putAll(stringCacheMap);
        }

        return result;
    }
}
