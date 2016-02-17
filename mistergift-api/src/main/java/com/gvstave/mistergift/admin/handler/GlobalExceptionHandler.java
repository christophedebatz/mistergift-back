package com.gvstave.mistergift.admin.handler;

import com.gvstave.mistergift.admin.response.ErrorResponse;
import com.gvstave.mistergift.admin.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 */
@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler
    protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {

        Response response = Response.withError(
            ErrorResponse.fromException(exception, HttpStatus.INTERNAL_SERVER_ERROR.value())
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return handleExceptionInternal(exception, response,
                headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}