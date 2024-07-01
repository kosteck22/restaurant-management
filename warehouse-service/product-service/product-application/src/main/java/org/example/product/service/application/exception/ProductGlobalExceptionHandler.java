package org.example.product.service.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.application.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class ProductGlobalExceptionHandler extends GlobalExceptionHandler {

}