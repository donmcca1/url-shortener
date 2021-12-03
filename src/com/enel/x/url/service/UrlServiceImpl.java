package com.enel.x.url.service;

import java.net.URI;

import com.enel.x.url.repository.UrlRepo;
import com.enel.x.url.service.domain.UriProcessor;

/**
 * Url shortener business logic implementation.
 */
public class UrlServiceImpl implements UrlService {
    private final UrlRepo repo;

    public UrlServiceImpl(UrlRepo repo) {
        this.repo = repo;
    }

    @Override
    public URI shorten(final URI fullUrl, final URI baseUrl) {
        return UriProcessor.shortener(repo, baseUrl).process(fullUrl);
    }

    @Override
    public URI resolve(final URI shortUrl) {
        return UriProcessor.resolver(repo).process(shortUrl);
    }
}