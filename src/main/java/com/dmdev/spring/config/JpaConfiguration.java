package com.dmdev.spring.config;

import com.dmdev.spring.config.conditional.JpaConditional;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Conditional(JpaConditional.class)
@Configuration
public class JpaConfiguration {

    @PostConstruct
    public void init(){
        System.out.println("JpaConfiguration initialization bean");
    }
}
