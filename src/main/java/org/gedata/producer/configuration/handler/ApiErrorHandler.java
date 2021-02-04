package org.gedata.producer.configuration.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> notFoundException(NoSuchElementException ex, WebRequest request){
        ApiError apiError = new ApiError(404, new Date(),ex.getMessage(),"Operation can not be processed.");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> jsonProcessingException(JsonProcessingException ex, WebRequest request){
        ApiError apiError = new ApiError(400, new Date(),ex.getMessage(),
                "jsonModel property has wrong format. Please make sure that jsonModel has valid format.");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> jsonProcessingException(InvalidParameterException ex, WebRequest request){
        ApiError apiError = new ApiError(400, new Date(),ex.getMessage(),
                "jsonModel property has wrong format. Please make sure that jsonModel has valid format.");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpelParseException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> jsonProcessingException(SpelParseException ex, WebRequest request){
        ApiError apiError = new ApiError(400, new Date(),ex.getMessage(),
                "Generator expression has wrong format. Should be: ${generatorName()} or ${generatorName()}${secondGenerator()}");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StringIndexOutOfBoundsException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> jsonProcessingException(StringIndexOutOfBoundsException ex, WebRequest request){
        ApiError apiError = new ApiError(400, new Date(),ex.getMessage(),
                "Generator expression has wrong format. Should be: ${generatorName()} or ${generatorName()}${secondGenerator()}");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpelEvaluationException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> jsonProcessingException(SpelEvaluationException ex, WebRequest request){
        ApiError apiError = new ApiError(404, new Date(),ex.getMessage().replaceAll(" on type org.gedata.producer.generator.InputInterpreter","."),
                "Generator not found. Make sure that generator is on the list v1/producer/generator");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
