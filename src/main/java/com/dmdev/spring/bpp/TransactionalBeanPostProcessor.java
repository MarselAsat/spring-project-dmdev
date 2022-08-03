package com.dmdev.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionalBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> transactionBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Transactional.class)){
            transactionBeans.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = transactionBeans.get(beanName);
        if (beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("Transaction open...");
                        try {
                            return method.invoke(bean, args);
                        }finally {
                            System.out.println("Transaction close...");
                        }
                    });
        }
        return bean;
    }
}
