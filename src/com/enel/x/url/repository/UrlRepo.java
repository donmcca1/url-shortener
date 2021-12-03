package com.enel.x.url.repository;

import java.net.URI;
import java.util.Optional;

/**
 * Interface to persist urls.
 */
public interface UrlRepo {
    static UrlRepo instance() {
        return InMemoryUrlRepo.INSTANCE;
    }

    Integer putIfAbsent(URI fullUrl);
    Optional<URI> getLongUrl(Integer id);
}
