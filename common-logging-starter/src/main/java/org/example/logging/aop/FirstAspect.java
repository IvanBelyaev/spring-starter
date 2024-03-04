package org.example.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
public class FirstAspect {

    /*
        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
     */
    @Pointcut("execution(public * org.example.*.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Before(value = "anyFindByIdServiceMethod()" +
            "&& args(id)" +
            "&& target(service)" +
            "&& this(serviceProxy)" +
            "&& @within(transactional)", argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLoggingBefore(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
        log.info("before - invoked findById method from {} with id = {}", service, id);
    }

    @AfterReturning(value = "anyFindByIdServiceMethod()" +
            "&& target(service)", returning = "result", argNames = "service, result")
    public void addLoggingAfterReturning(Object service, Object result) {
        log.info("after returning - invoked findById method from {}, result - {}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod()" +
            "&& target(service)", throwing = "ex")
    public void addLoggingAfterThrowing(Object service, Throwable ex) {
        log.info("after throwing - invoked findById method from {}, exception - {}: {}",
                service, ex.getClass(), ex.getMessage());
    }

    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterFinally(Object service) {
        log.info("after finally - invoked findById method from {}", service);
    }
}
