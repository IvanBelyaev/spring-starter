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
public class TransactionBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> transactionBean = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
            transactionBean.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = transactionBean.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(), beanClass.getInterfaces(),
                    (Object proxy, Method method, Object[] args) -> {
                        log.info("Start transaction...");
                        Object retVal = method.invoke(bean, args);
                        log.info("Commit transaction...");
                        return retVal;
                    });
        }
        return bean;
    }
}
