package com.flowable.common.session;

public interface Session {
    void flush();

    void close();
}
