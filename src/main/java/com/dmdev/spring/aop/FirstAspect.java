package com.dmdev.spring.aop;


import com.dmdev.spring.http.controller.UserController;
import com.dmdev.spring.validation.UserInfo;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Aspect
@Component
public class FirstAspect {

    @Pointcut("within(com.dmdev.spring.http.controller.*Controller)")
    public void isControllerLayer(){
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer(){}

    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("that(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer(){}


    @Pointcut("isController() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping(){}

    @Pointcut("isController() && args(org.springframework.ui.Model,..)")
    public void hasModelParam(){
    }

    @Pointcut("isController() && @args(com.dmdev.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation(){}

    @Pointcut("bean(*Service)")
    public void isServiceLayerBean(){}

    @Pointcut("execution(public * findBuId(*))")
    public void anyFindByIdServiceMethod(){}
}
