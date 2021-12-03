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

package com.enel.x.url.service.domain.exception;

import com.enel.x.url.http.ApplicationHttpException;
import com.enel.x.url.http.ResponseCodes;

/**
 * Exception indicting url cant be shortened.
 */
public class CantShortenUrlException extends ApplicationHttpException {
    public CantShortenUrlException(final String message) {
        super(message, ResponseCodes.BAD_REQUEST);
    }
}
