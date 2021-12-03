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

package com.enel.x.url.http;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Base handler that resources should extend and override methods to implement matching http method. Subclasses should also
 * override supported http methods to indicate the supported http methods of the resource.
 */
public abstract class BaseHandler implements HttpHandler {

    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(HttpMethods.GET.toString())) {
            doGet(exchange);
        } else if (exchange.getRequestMethod().equals(HttpMethods.POST.toString())) {
            doPost(exchange);
        } else {
            handleUnsupported(exchange);
        }
    }

    protected void doPost(final HttpExchange exchange) throws IOException {
        handleUnsupported(exchange);
    }

    protected void doGet(final HttpExchange exchange) throws IOException {
        handleUnsupported(exchange);
    }

    protected void handleApplicationException(final ApplicationHttpException exception, final HttpExchange exchange) throws IOException {
        var responseText = "Encountered exception while processing request: %s".formatted(exception.getMessage());
        exchange.sendResponseHeaders(exception.getStatusCode(), responseText.length());
        try (var os = exchange.getResponseBody()) {
            os.write(responseText.getBytes());
        }
    }

    private void handleUnsupported(final HttpExchange exchange) throws IOException {
        var responseText =  "This resource does not support Http Method: %s".formatted(exchange.getRequestMethod());
        exchange.getResponseHeaders().set(HttpHeaders.Allow, supportedMethods().stream().map(HttpMethods::toString).collect(Collectors.joining(",")));
        exchange.sendResponseHeaders(ResponseCodes.METHOD_NOT_ALLOWED, responseText.length());
        try (var os = exchange.getResponseBody()) {
            os.write(responseText.getBytes());
        }
    }

    public abstract Set<HttpMethods> supportedMethods();
}
