package com.gvstave.mistergift.api.handler;

import com.gvstave.mistergift.api.response.ErrorResponse;
import com.gvstave.mistergift.api.response.Response;
import com.gvstave.mistergift.data.exception.DuplicatedEntityException;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
    @ExceptionHandler
    public ResponseEntity<Response> defaultErrorHandler(HttpServletRequest httpServletRequest, Exception exception) throws Exception {
        LOGGER.error("Error has been thrown!", exception);
        Response response = Response.withError(createErrorResponse(exception));
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getError().getStatus()));
    }

    /**
     * Returns the status code according to the given exception.
     *
     * @param exception The raised exception.
     * @return The status code.
     */
    private ErrorResponse createErrorResponse(Exception exception) {
        if (exception instanceof DuplicatedEntityException) {
            return ErrorResponse.fromException(exception, HttpStatus.CONFLICT.value());
        } else if (exception instanceof InvalidFieldValueException) {
            InvalidFieldValueException ex = (InvalidFieldValueException) exception;
            ErrorResponse response = ErrorResponse.fromException(exception, HttpStatus.BAD_REQUEST.value());
            Optional.ofNullable(ex.getFields())
                .map(name -> response.addParameter("field", name));
        } else if (exception instanceof UnauthorizedOperationException) {
            return ErrorResponse.fromException(exception, HttpStatus.UNAUTHORIZED.value());
        } else if (exception instanceof EntityNotFoundException) {
            return ErrorResponse.fromException(exception, HttpStatus.NOT_FOUND.value());
        }

        return ErrorResponse.fromException(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}