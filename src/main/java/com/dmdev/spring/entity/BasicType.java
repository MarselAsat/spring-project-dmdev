package com.dmdev.spring.entity;

import java.io.Serializable;

public interface BasicType<T extends Serializable> {
    T getId();

    void setId(T id);
}
