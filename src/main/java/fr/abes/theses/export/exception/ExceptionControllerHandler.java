package fr.abes.theses.export.exception;

import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    @ExceptionHandler(FileNotFoundException.class)
    protected ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex) {
        String error = "FileNotFoundException...";
        log.debug(error + " " + ex.getLocalizedMessage());
        return buildResponseEntity(new ApiReturnError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(TransformerException.class)
    protected ResponseEntity<Object> handleTransformerException(TransformerException ex) {
        String error = "TransformerException...";
        log.debug(error + " " + ex.getLocalizedMessage());
        return buildResponseEntity(new ApiReturnError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(JDOMException.class)
    protected ResponseEntity<Object> handleJDOMException(JDOMException ex) {
        String error = "JDOMException (bibtex, ris)...";
        log.debug(error + " " + ex.getLocalizedMessage());
        return buildResponseEntity(new ApiReturnError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIOException(IOException ex) {
        String error = "IOException (bibtex, ris)...";
        log.debug(error + " " + ex.getLocalizedMessage());
        return buildResponseEntity(new ApiReturnError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }
}
