package com.enel.x.url.repository;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * An in memory non-persistent repo. Implemented as enum so is singleton.
 */
public enum InMemoryUrlRepo implements UrlRepo {
    INSTANCE;

    private final Map<URI, Integer> longCache = new HashMap<>();
    private final Map<Integer, URI> shortCache = new HashMap<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Integer putIfAbsent(final URI fullUrl) {
        lock.writeLock().lock();
        try {
            var count = longCache.size() + 1;
            var result = longCache.computeIfAbsent(fullUrl, key -> count);
            shortCache.putIfAbsent(result, fullUrl);
            return result;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<URI> getLongUrl(final Integer id) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(shortCache.get(id));
        } finally {
            lock.readLock().unlock();
        }
    }
}
