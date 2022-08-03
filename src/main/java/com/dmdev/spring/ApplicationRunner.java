package com.dmdev.spring;

import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.pool.ConnectionPool;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        String value = "hello";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));

        try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");) {
            ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
            CrudRepository companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println();
            System.out.println(companyRepository.findById(1));
        }

    }
}
