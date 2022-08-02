package com.dmdev.spring.repository;

import com.dmdev.spring.bpp.InjectBeans;
import com.dmdev.spring.pool.ConnectionPool;

public class CompanyRepository {

    @InjectBeans
    private ConnectionPool connectionPool;
}
