package org.example.invoice.service.application.exception.handler;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.application.handler.ErrorDTO;
import org.example.application.handler.GlobalExceptionHandler;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class InvoiceGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {InvoiceDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(InvoiceDomainException invoiceDomainException, HttpServletRequest request) {
        log.error(invoiceDomainException.getMessage(), invoiceDomainException);
        return new ErrorDTO(
                request.getRequestURI(),
                invoiceDomainException.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date());
    }
}
