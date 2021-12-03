package com.enel.x.url.service.domain;

import java.net.URI;

import com.enel.x.url.repository.UrlRepo;

/**
 * Interface covering a uri processing step, meant to be implemented as a decorator with an inner processor ending with an identity processor.
 */
public interface UriProcessor {
    static UriProcessor shortener(UrlRepo repo, URI baseUrl) {
        return new UriValidator(new UriShortener(UriProcessor.identity(), repo, baseUrl));
    }

    static UriProcessor resolver(UrlRepo repo) {
        return new UriValidator(new UriResolver(UriProcessor.identity(), repo));
    }

    URI process(URI uri);

    static UriProcessor identity() {
        return uri -> uri;
    }
}
