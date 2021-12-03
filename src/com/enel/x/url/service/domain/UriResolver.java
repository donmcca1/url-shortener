package com.enel.x.url.service.domain;

import java.net.URI;

import com.enel.x.url.repository.UrlRepo;
import com.enel.x.url.service.domain.exception.InvalidShortUrlException;
import com.enel.x.url.service.domain.exception.NoSuchShortUrlException;

public class UriResolver implements UriProcessor {

    private UriProcessor inner;
    private UrlRepo repo;

    public UriResolver(UriProcessor inner, UrlRepo repo) {
        this.inner = inner;
        this.repo = repo;
    }

    @Override
    public URI process(final URI uri) {
        try {
            var id = Integer.parseInt(uri.getPath().replaceFirst("/", ""), 16);
            return inner.process(repo.getLongUrl(id).orElseThrow(() -> new NoSuchShortUrlException("Could not find short url: %s".formatted(uri))));
        } catch (NumberFormatException exception) {
            throw new InvalidShortUrlException("The requested short url does not contain a valid short url id: %s".formatted(uri.getPath()));
        }
    }
}
