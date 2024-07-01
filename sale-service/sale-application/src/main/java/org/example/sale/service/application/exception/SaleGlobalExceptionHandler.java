package org.example.sale.service.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.application.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Slf4j
@ControllerAdvice
public class SaleGlobalExceptionHandler  extends GlobalExceptionHandler {
}
