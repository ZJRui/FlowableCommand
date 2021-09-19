package com.flowable.common.transaction;

import com.flowable.common.cfg.CommandContext;

public interface TransactionListener {
    void execute(CommandContext commandContext);
}
