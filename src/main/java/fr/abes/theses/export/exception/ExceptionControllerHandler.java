package fr.abes.theses.export.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiReturnError apiReturnError) {
        return new ResponseEntity<>(apiReturnError, apiReturnError.getStatus());
    }


    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        String error = "NullPointer...";
        log.debug(error + " " + ex.getLocalizedMessage());
        return buildResponseEntity(new ApiReturnError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<Object> handleNullPointerException(HttpClientErrorException.BadRequest ex) {
        String error = "BadRequest...";
        log.debug(error + " " + ex.getLocalizedMessage());
        return buildResponseEntity(new ApiReturnError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        String error = "EmptyResultDataAccessException...";
        log.debug(error + " " + ex.getLocalizedMessage());
        return buildResponseEntity(new ApiReturnError(HttpStatus.BAD_REQUEST, error, ex));
    }

}
