package com.enel.x.url.service.domain;

import java.net.URI;

import com.enel.x.url.service.domain.exception.UnparseableUriException;

/**
 * Processing step to validate a uri.
 */
public class UriValidator implements UriProcessor {
    private final UriProcessor inner;

    public UriValidator(final UriProcessor inner) {
        this.inner = inner;
    }

    @Override
    public URI process(final URI uri) {
        if (!uri.isAbsolute()) {
            throw new UnparseableUriException("The requested URI is not absolute. Please provide a scheme component in the URI, example http://. URI=%s".formatted(uri));
        }
        return inner.process(uri);
    }
}
