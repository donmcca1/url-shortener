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
import com.enel.x.url.http.HttpMethods;
import com.enel.x.url.http.ResponseCodes;
import com.enel.x.url.service.UrlService;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handler for /url, resource for creating a shortened version of a url.
 */
public class UrlHandler extends BaseHandler {

    private final UrlService urlService;

    public UrlHandler(UrlService urlService) {
        this.urlService = urlService;
    }

    @Override
    public void doPost(final HttpExchange exchange) throws IOException {
        try {
            try (var is = exchange.getRequestBody()) {
                var requestBytes = is.readAllBytes();
                var requestString = new String(requestBytes);
                var requestUrl = URI.create(requestString);
                var baseUrl = new URI("http:/%s".formatted(exchange.getLocalAddress().toString()));
                var responseUrl = urlService.shorten(requestUrl, baseUrl);
                var responseText = responseUrl.toString();
                exchange.sendResponseHeaders(ResponseCodes.SUCCESS, responseText.length());
                try (var os = exchange.getResponseBody()) {
                    os.write(responseText.getBytes());
                }
            }
        } catch (ApplicationHttpException exception) {
            handleApplicationException(exception, exchange);
        } catch (Exception exception) {
            handleApplicationException(new ApplicationHttpException(exception.getMessage()), exchange);
        }
    }

    @Override
    public Set<HttpMethods> supportedMethods() {
        return Collections.singleton(HttpMethods.POST);
    }
}
