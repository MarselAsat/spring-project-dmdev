package com.dmdev.spring.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {

    @EventListener
    public void acceptEntity(EventEntity entity){
        System.out.println("Entity: " + entity);
    }
}
