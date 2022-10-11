package com.dmdev.spring.aop;


import com.dmdev.spring.http.controller.UserController;
import com.dmdev.spring.validation.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
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


    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping(){}

    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam(){
    }

    @Pointcut("isControllerLayer() && @args(com.dmdev.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation(){}

    @Pointcut("bean(*Service)")
    public void isServiceLayerBean(){}

    @Pointcut("execution(public * findById(*))")
    public void anyFindByIdServiceMethod(){}

    @Before("anyFindByIdServiceMethod() " +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy) " +
            "&& @within(transactional)")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional){
        log.info("invoke findById method");
    }

    @AfterReturning(value = "anyFindByIdServiceMethod() " +
                    "&& target(service)", returning = "result")
    public void addLoggingAfterReturning(Object service,
            Object result){
        log.info("after returning - invoke findById method, class {}, result - {}", service, result);
    }
    @AfterThrowing(value = "anyFindByIdServiceMethod() " +
                    "&& target(service)", throwing = "ex")
    public void addLoggingAfterThrowing(Object service,
            Throwable ex){
        log.info("after throwing - invoke findById method, class {}, exception - {}", service, ex);
    }
    @After(value = "anyFindByIdServiceMethod() " +
                    "&& target(service)")
    public void addLoggingAfterReturning(Object service){
        log.info("after - invoke findById method, class {}", service);
    }

    @Around(value = "anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingAround(ProceedingJoinPoint proceeding, Object service, Object id) throws Throwable {
        log.info("AROUND before - invoke findById method in class {}, with id {}", service, id);
        try {
            Object result = proceeding.proceed();
            log.info("AROUND after returning - invoke findById method in class {}, result - {}", service, result);
            return result;
        }catch (Throwable ex) {
            log.info("AROUND after throwing - invoke findById method in class {}, exception {}", service, ex);
            throw ex;
        }finally {
            log.info("AROUND after - invoke findById method in class {}", service);
        }
    }

}
