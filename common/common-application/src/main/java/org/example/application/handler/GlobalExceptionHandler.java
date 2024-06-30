package org.example.application.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ValidationException exception, HttpServletRequest request) {
        ErrorDTO errorDTO;
        if (exception instanceof ConstraintViolationException validationException) {
            String violations = extractViolationsFromException(validationException);
            log.error(violations, validationException);
            errorDTO = new ErrorDTO(
                    request.getRequestURI(),
                    violations,
                    HttpStatus.BAD_REQUEST.value(),
                    new Date());

        } else {
            String exceptionMessage = exception.getMessage();
            log.error(exceptionMessage, exception);
            errorDTO = new ErrorDTO(
                    request.getRequestURI(),
                    exceptionMessage,
                    HttpStatus.BAD_REQUEST.value(),
                    new Date());
        }
        return errorDTO;
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception exception, HttpServletRequest request) {
        log.error(exception.getMessage(), exception);
        return new ErrorDTO(
                request.getRequestURI(),
                "Unexpected error!",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date()
        );
    }

    private String extractViolationsFromException(ConstraintViolationException validationException) {
        Map<String, String> fieldErrors = validationException.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));

        return fieldErrors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
    }
}
