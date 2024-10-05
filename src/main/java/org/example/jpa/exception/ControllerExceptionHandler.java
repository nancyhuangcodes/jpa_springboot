package org.example.jpa.exception;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;


@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (ResourceNotFoundException.class)
    protected ResponseEntity<Object> handlerResourceNotFoundException(ResourceNotFoundException ex) {

        Map<String, String> response = new HashMap<>();

        response.put("error", ex.getMessage()); // What is the value returned from getMessage()

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // handle/trap errors produced by @RequestBody annotation binding (e.g. no data sent in the request's body)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders httpHeaders, HttpStatusCode httpStatusCode, WebRequest request){

        MessageNotReadableException messageNotReadableException = new MessageNotReadableException();

        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", messageNotReadableException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // handle/trap errors produced by @Valid annotation bindings
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders httpHeaders, HttpStatusCode httpStatusCode, WebRequest request){

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        Collections.sort(errors);   // formats the listed errors by alphabetical order

        Map<String, List<String>> errorResponse = new HashMap<>();

        errorResponse.put("error", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
