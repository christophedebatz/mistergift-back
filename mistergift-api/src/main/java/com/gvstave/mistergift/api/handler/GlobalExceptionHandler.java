package com.gvstave.mistergift.api.handler;

import com.gvstave.mistergift.api.response.ErrorResponse;
import com.gvstave.mistergift.api.response.Response;
import com.gvstave.mistergift.data.exception.DuplicatedEntityException;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * The main exception handler.
 */
@ControllerAdvice
class GlobalExceptionHandler {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * The default handling exception.
     *
     * @param httpServletRequest The http servlet request.
     * @param exception The exception.
     * @return The serialized object.
     * @throws Exception The given exception.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class, TooManyRequestException.class })
    public ResponseEntity<Response> defaultErrorHandler(HttpServletRequest httpServletRequest, Exception exception) throws Exception {
        LOGGER.error("Error has been thrown !", exception);
        HttpStatus status = getHttpStatusCode(exception);

        Response response = Response.withError(
            ErrorResponse.fromException(exception, status.value())
        );

        return new ResponseEntity<>(response, status);
    }

    /**
     * Returns the status code according to the given exception.
     *
     * @param exception The raised exception.
     * @return The status code.
     */
    private HttpStatus getHttpStatusCode(Exception exception) {
        HttpStatus status;

        if (exception instanceof DuplicatedEntityException) {
            status = HttpStatus.CONFLICT;
        } else if (exception instanceof InvalidFieldValueException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof UnauthorizedOperationException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof TooManyRequestException) {
            status = HttpStatus.TOO_MANY_REQUESTS;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return status;
    }

}