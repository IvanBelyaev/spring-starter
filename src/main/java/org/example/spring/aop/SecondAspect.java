package org.example.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class SecondAspect {

    @Around(value = "org.example.spring.aop.FirstAspect.anyFindByIdServiceMethod() " +
            "&& target(service) " +
            "&& args(id)", argNames = "joinPoint,service,id")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.info("AROUND before - invoked findById method from {} with id = {}", service, id);
        try {
            Object result = joinPoint.proceed();
            log.info("AROUND after returning - invoked findById method from {}, result - {}", service, result);
            return result;
        } catch (Throwable ex) {
            log.info("AROUND after throwing - invoked findById method from {}, exception - {}: {}",
                    service, ex.getClass(), ex.getMessage());
            throw ex;
        } finally {
            log.info("AROUND after finally - invoked findById method from {}", service);
        }
    }
}
