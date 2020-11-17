package com.simpleindex.scannerservice.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class ControllerExceptionHandler extends DefaultHandlerExceptionResolver {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> exception(HttpRequestMethodNotSupportedException exception) {
        LOG.error("Method not supported error={}", exception.getMessage());
        return new ResponseEntity<>("Not Supported", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> exception(RuntimeException exception) {
        LOG.error("Runtime exception={}", exception.getMessage());
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
}
