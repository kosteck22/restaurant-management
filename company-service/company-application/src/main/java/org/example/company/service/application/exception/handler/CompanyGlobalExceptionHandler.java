package org.example.company.service.application.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.application.handler.ErrorDTO;
import org.example.application.handler.GlobalExceptionHandler;
import org.example.company.service.domain.exception.CompanyDomainException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class CompanyGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {CompanyDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(CompanyDomainException companyDomainException, HttpServletRequest request) {
        log.error(companyDomainException.getMessage(), companyDomainException);
        return new ErrorDTO(
                request.getRequestURI(),
                companyDomainException.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date());
    }
}
