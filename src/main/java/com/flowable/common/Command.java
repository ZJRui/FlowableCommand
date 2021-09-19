package com.flowable.common;

public interface Command<T>{

    T execute();
}

