/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2021
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.enel.x.url.service.domain;

import java.net.URI;
import java.net.URISyntaxException;

import com.enel.x.url.repository.UrlRepo;
import com.enel.x.url.service.domain.exception.CantShortenUrlException;

/**
 * Processing step to shorten a url.
 */
public class UriShortener implements UriProcessor {
    private final UriProcessor inner;
    private UrlRepo repo;
    private URI baseUri;

    public UriShortener(final UriProcessor inner, UrlRepo repo, URI baseUri) {
        this.inner = inner;
        this.repo = repo;
        this.baseUri = baseUri;
    }

    @Override
    public URI process(final URI uri) {
        var id = repo.putIfAbsent(uri);
        try {

            var shortened = new URI(baseUri.toString() + "/" + Integer.toHexString(id));
            if (uri.toString().length() <= shortened.toString().length()) {
                throw new CantShortenUrlException("Unable to shorten url: %s. Computed short version: %s".formatted(uri, shortened));
            }
            return inner.process(shortened);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
