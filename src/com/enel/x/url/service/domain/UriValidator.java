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
