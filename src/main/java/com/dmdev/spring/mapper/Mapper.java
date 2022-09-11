package com.dmdev.spring.mapper;

public interface Mapper<T, F> {
    T mapTo(F object);

    default T mapTo(F from, T to){
        return to;
    }
}
