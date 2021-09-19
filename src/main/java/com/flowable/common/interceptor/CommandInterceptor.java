package com.flowable.common.interceptor;


import com.flowable.common.Command;
import com.flowable.common.cfg.CommandConfig;
import com.flowable.common.executor.CommandExecutor;

public interface CommandInterceptor {
    <T> T execute(CommandConfig config, Command<T> command, CommandExecutor commandExecutor);

    CommandInterceptor getNext();

    void setNext(CommandInterceptor next);

}
