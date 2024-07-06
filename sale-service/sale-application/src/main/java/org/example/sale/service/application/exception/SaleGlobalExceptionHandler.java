package org.example.sale.service.application.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.application.handler.ErrorDTO;
import org.example.application.handler.GlobalExceptionHandler;
import org.example.sale.service.domain.exception.SaleDomainException;
import org.example.sale.service.domain.exception.SaleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;


@Slf4j
@ControllerAdvice
public class SaleGlobalExceptionHandler  extends GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = {SaleDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(SaleDomainException saleDomainException, HttpServletRequest request) {
        log.error(saleDomainException.getMessage(), saleDomainException);
        return new ErrorDTO(
                request.getRequestURI(),
                saleDomainException.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date());
    }

    @ResponseBody
    @ExceptionHandler(value = {SaleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(SaleNotFoundException saleNotFoundException, HttpServletRequest request) {
        log.error(saleNotFoundException.getMessage(), saleNotFoundException);
        return new ErrorDTO(
                request.getRequestURI(),
                saleNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                new Date());
    }
}
