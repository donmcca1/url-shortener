package com.enel.x.url.service;

import java.net.URI;

import com.enel.x.url.repository.UrlRepo;

/**
 * Interface covering business logic of url shortener service.
 */
public interface UrlService {
    static UrlService instance() {
        return new UrlServiceImpl(UrlRepo.instance());
    }

    URI shorten(URI fullUrl, URI baseUrl);

    URI resolve(URI shortUrl);
}
