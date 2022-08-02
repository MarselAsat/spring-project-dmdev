package com.dmdev.spring;

import com.dmdev.spring.pool.ConnectionPool;
import com.dmdev.spring.repository.CompanyRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");) {
            ConnectionPool connectionPool = context.getBean("p1", ConnectionPool.class);
            CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println();
        }

    }
}
