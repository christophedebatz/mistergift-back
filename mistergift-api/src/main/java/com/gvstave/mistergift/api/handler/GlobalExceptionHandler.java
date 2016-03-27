package com.gvstave.mistergift.api.handler;

import com.gvstave.mistergift.api.response.ErrorResponse;
import com.gvstave.mistergift.api.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * The main exception handler.
 */
@ControllerAdvice
class GlobalExceptionHandler {

    /**
     * The default handling exception.
     *
     * @param httpServletRequest The http servlet request.
     * @param exception The exception.
     * @return The serialized object.
     * @throws Exception The given exception.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> defaultErrorHandler(HttpServletRequest httpServletRequest, Exception exception) throws Exception {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        Response response = Response.withError(
            ErrorResponse.fromException(exception, status.value())
        );

        return new ResponseEntity<>(response, status);
    }

}