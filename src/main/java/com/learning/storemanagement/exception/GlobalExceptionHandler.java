package com.learning.storemanagement.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.learning.storemanagement.model.Role;
import com.learning.storemanagement.utils.events.ExceptionEvent;
import com.learning.storemanagement.utils.LoggerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        String source = extractSourceClass(ex);
        LoggerUtility.logEventWarn(LOGGER, ExceptionEvent.VALIDATION_ERROR,source,errors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Not found");
        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormat(InvalidFormatException ex) {
        if(ex.getTargetType().isEnum()) {
            String fieldName = ex.getPath().get(0).getFieldName();
            String errorMessage = String.format("Invalid value for field '%s'. Allowed values: %s", fieldName, Arrays.toString(Role.values()));

            LoggerUtility.logEnumParseExWarn(LOGGER, ExceptionEvent.INVALID_ENUM,fieldName, errorMessage);

            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private String extractSourceClass(MethodArgumentNotValidException ex) {
        Object target = ex.getBindingResult().getTarget();
        return target != null ? target.getClass().getSimpleName() : "Unknown";
    }
}
