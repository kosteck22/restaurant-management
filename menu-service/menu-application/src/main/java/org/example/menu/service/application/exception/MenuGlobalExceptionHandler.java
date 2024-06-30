package org.example.menu.service.application.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.application.handler.ErrorDTO;
import org.example.application.handler.GlobalExceptionHandler;
import org.example.menu.service.domain.exception.MenuItemDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class MenuGlobalExceptionHandler extends GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = {MenuItemDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(MenuItemDomainException menuItemDomainException, HttpServletRequest request) {
        log.error(menuItemDomainException.getMessage(), menuItemDomainException);
        return new ErrorDTO(
                request.getRequestURI(),
                menuItemDomainException.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date());
    }
}
