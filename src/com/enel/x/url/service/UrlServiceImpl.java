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

package com.enel.x.url.service;

import java.net.URI;
import java.util.Optional;

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