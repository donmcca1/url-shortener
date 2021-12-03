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

/**
 * Runtime exception that will be transformed to a http response with a given http response code.
 */
public class ApplicationHttpException extends RuntimeException {
    private final int statusCode;

    public ApplicationHttpException(final String message) {
        this(message, ResponseCodes.INTERNAL_SERVER_ERROR);
    }

    public ApplicationHttpException(final String message, final int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApplicationHttpException(final String message, final Throwable cause) {
        this(message, cause, ResponseCodes.INTERNAL_SERVER_ERROR);
    }

    public ApplicationHttpException(final String message, final Throwable cause, final int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    /**
     * Gets statusCode.
     *
     * @return value of statusCode.
     */
    public int getStatusCode() {
        return statusCode;
    }
}
