package com.flowable.common.interceptor.impl;

import com.flowable.common.Command;
import com.flowable.common.cfg.CommandConfig;
import com.flowable.common.executor.CommandExecutor;
import com.flowable.common.interceptor.AbstractCommandInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInterceptor  extends AbstractCommandInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public <T> T execute(CommandConfig config, Command<T> command, CommandExecutor commandExecutor) {
        if(!LOGGER.isDebugEnabled()){
            return next.execute(config, command, commandExecutor);
        }
        LOGGER.debug("---------------starting {} -----------------------",command.getClass().getSimpleName());
        try{
            return next.execute(config, command, commandExecutor);
        }finally {
            LOGGER.debug("---------------{} finished --------------", command.getClass().getSimpleName());
        }

    }
}
