package com.flowable.common;

import com.flowable.common.cfg.CommandContext;

public interface Command<T>{

    T execute(CommandContext commandContext);

}

