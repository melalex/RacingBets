package com.room414.racingbets.dal.concrete.caching.infrastructure.pool;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alexander Melashchenko
 * @version 1.0 17 Mar 2017
 */
// TODO: is map should be concurrent save?
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

    Map<String, Cache> build() {
        return toBuild.stream()
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
