package com.flowable.common.interceptor.impl;

import com.flowable.common.Command;
import com.flowable.common.cfg.CommandConfig;
import com.flowable.common.cfg.CommandContext;
import com.flowable.common.cfg.CommandContextFactory;
import com.flowable.common.cfg.Context;
import com.flowable.common.executor.CommandExecutor;
import com.flowable.common.interceptor.AbstractCommandInterceptor;

public class CommandContextInterceptor  extends AbstractCommandInterceptor {


    protected CommandContextFactory commandContextFactory;
    protected ClassLoader classLoader;

    @Override
    public <T> T execute(CommandConfig config, Command<T> command, CommandExecutor commandExecutor) {
        CommandContext commandContext = Context.getCommandContext();


        /*
         * This flag indicates whether the context is reused for the execution of the current command.
         * If a valid command context exists, this means a nested service call is being executed.
         * If so, this flag will change to 'true', with the purpose of closing the command context in the finally block.
         *
         * *该标志指示当前命令执行时上下文是否被重用。
         *如果存在有效的命令上下文，这意味着正在执行嵌套的服务调用。
         *如果是，该标志将更改为'true'，目的是在finally块中关闭命令上下文。
         */
        boolean contextReused = false;

        return null;
    }
}
