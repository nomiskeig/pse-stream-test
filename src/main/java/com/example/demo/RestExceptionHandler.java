package com.example.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
/*
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("Error thrown");
        String error = "Malformed JSON request";
        return ResponseEntity.ok("test");
        // return buildResponseEntity(new ApiError(HttpStatus.GONE, error));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    // other exception handlers below

}
*/
@ControllerAdvice
public class RestExceptionHandler {
  
  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<Object> resourceNotFoundException(MissingServletRequestPartException ex, WebRequest request) {
       
        System.out.println(ex.getMessage());
    
    return buildResponseEntity(new ApiError(HttpStatus.GONE, ex.getMessage()));
  }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
