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
