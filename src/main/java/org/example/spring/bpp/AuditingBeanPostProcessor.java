package org.example.spring.bpp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuditingBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> auditingBean = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Auditing.class)) {
            auditingBean.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = auditingBean.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    (Object proxy, Method method, Object[] args) -> {
                        log.info("Start auditing...");
                        long startTime = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long endTime = System.nanoTime();
                        log.info("End auditing time = " + (endTime - startTime) + "ns");
                        return retVal;
                    });
        }
        return bean;
    }
}
