package com.dmdev.spring.repository;

import com.dmdev.spring.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Repository
@RequiredArgsConstructor
public class UserRepository {

    @Qualifier("pool2")
    private final ConnectionPool connectionPool;

}
