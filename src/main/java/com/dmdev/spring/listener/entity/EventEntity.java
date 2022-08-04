package com.dmdev.spring.listener.entity;

import java.util.EventObject;

public class EventEntity extends EventObject {
    private final AccessType accessType;

    public EventEntity(Object entity, AccessType accessType) {
        super(entity);
        this.accessType = accessType;
    }
    public AccessType getAccessType() {
        return accessType;
    }

}
