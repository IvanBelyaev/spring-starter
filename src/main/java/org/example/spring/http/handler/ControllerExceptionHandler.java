package org.example.spring.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice(basePackages = "org.example.spring.http.controller")
public class ControllerExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public String handleExceptions(Exception exception) {
//        log.error("Failed to return response", exception);
//        return "error/error500";
//    }
}
