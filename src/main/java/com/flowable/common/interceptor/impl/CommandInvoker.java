package com.flowable.common.interceptor.impl;

import com.flowable.common.Command;
import com.flowable.common.cfg.CommandConfig;
import com.flowable.common.cfg.CommandContext;
import com.flowable.common.cfg.Context;
import com.flowable.common.executor.CommandExecutor;
import com.flowable.common.interceptor.AbstractCommandInterceptor;

public class CommandInvoker extends AbstractCommandInterceptor {


    @Override
    public <T> T execute(CommandConfig config, Command<T> command, CommandExecutor commandExecutor) {


        CommandContext commandContext = Context.getCommandContext();


        return command.execute(commandContext);
    }
}
