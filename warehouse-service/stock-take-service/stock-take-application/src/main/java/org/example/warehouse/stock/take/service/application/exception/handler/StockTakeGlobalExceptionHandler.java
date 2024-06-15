package org.example.warehouse.stock.take.service.application.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.application.handler.ErrorDTO;
import org.example.warehouse.stock.take.service.domain.exception.StockTakeDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class StockTakeGlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {StockTakeDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(StockTakeDomainException stockTakeDomainException, HttpServletRequest request) {
        log.error(stockTakeDomainException.getMessage(), stockTakeDomainException);
        return new ErrorDTO(
                request.getRequestURI(),
                stockTakeDomainException.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date());
    }
}
