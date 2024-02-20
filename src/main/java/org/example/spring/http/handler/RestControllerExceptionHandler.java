package org.example.spring.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "org.example.spring.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
