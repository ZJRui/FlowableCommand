package com.flowable.common.session;

import com.flowable.common.cfg.CommandContext;
import com.flowable.common.cfg.CommandContextFactory;

public interface   SessionFactory {

    Class<?> getSessionType();

    Session openSession(CommandContext commandContext);

}
