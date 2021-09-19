package com.flowable.common.executor;

import com.flowable.common.Command;
import com.flowable.common.cfg.CommandConfig;

public  interface CommandExecutor {


    CommandConfig getDefaultConfig();

    <T> T execute(CommandConfig config, Command<T> command);

    <T> T execute(Command<T> command);

}
