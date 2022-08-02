package com.dmdev.spring;

import com.dmdev.spring.pool.ConnectionPool;
import com.dmdev.spring.repository.CompanyRepository;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        String value = "hello";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));

        try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");) {
            ConnectionPool connectionPool = context.getBean("p1", ConnectionPool.class);
            CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println();
        }

    }
}
