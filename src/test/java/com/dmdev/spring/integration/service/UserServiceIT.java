package com.dmdev.spring.integration.service;

import com.dmdev.spring.integration.IntegrationTestBase;
import com.dmdev.spring.integration.annotation.IT;
import com.dmdev.spring.pool.ConnectionPool;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;


@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private final UserService userService;

    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void test(){
        System.out.println();
    }

    @Test
    void test2(){
        System.out.println();
    }
}
