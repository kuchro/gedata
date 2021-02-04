package org.gedata.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.gedata.producer.model.producer.ApiError;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> notFoundException(NoSuchElementException ex, WebRequest request){
        ApiError apiError = getApiError(404,ex.getMessage(),"Operation can not be processed.");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidParameterException.class,JsonProcessingException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> invalidParamsEx(Exception ex, WebRequest request){
        ApiError apiError = getApiError(400, ex.getMessage(),
                "jsonModel property has wrong format. Please make sure that jsonModel has valid format.");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SpelParseException.class,StringIndexOutOfBoundsException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> jsonProcessingException(Exception ex, WebRequest request){
        ApiError apiError =
                getApiError(400, ex.getMessage(),
                "Generator expression has wrong format. Should be: ${generatorName()} " +
                        "or ${generatorName()}${secondGenerator()}");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SpelEvaluationException.class,NoSuchMethodException.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> jsonParseMethodNotFound(Exception ex, WebRequest request){
        ApiError apiError = getApiError(404,ex.getMessage()
                        .replaceAll(" on type org.gedata.producer.generator.InputInterpreter","."),
                "Generator not found. Make sure that generator is on the list v1/producer/generator");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    private ApiError getApiError(int i, String message, String s) {
        return new ApiError(i, Instant.now(), message,
                s);
    }
}
