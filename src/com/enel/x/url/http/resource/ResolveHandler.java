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

package com.enel.x.url.http.resource;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Set;

import com.enel.x.url.http.ApplicationHttpException;
import com.enel.x.url.http.BaseHandler;
import com.enel.x.url.http.HttpHeaders;
import com.enel.x.url.http.HttpMethods;
import com.enel.x.url.http.ResponseCodes;
import com.enel.x.url.service.UrlService;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handler to resolve shortened urls.
 */
public class ResolveHandler extends BaseHandler {

    private final UrlService urlService;

    public ResolveHandler(UrlService urlService) {
        this.urlService = urlService;
    }

    @Override
    public void doGet(final HttpExchange exchange) throws IOException {
        try {
            var baseUrl = new URI("http:/%s%s".formatted(exchange.getLocalAddress().toString(), exchange.getRequestURI()));
            var redirectUrl =  urlService.resolve(baseUrl).toString();
            exchange.getResponseHeaders().set(HttpHeaders.Location, redirectUrl);
            exchange.sendResponseHeaders(ResponseCodes.PERMANENT_REDIRECT, -1);
            exchange.getResponseBody().close();
        } catch (ApplicationHttpException exception) {
            handleApplicationException(exception, exchange);
        } catch (Exception exception) {
            handleApplicationException(new ApplicationHttpException(exception.getMessage()), exchange);
        }
    }

    @Override
    public Set<HttpMethods> supportedMethods() {
        return Collections.singleton(HttpMethods.GET);
    }
}
