package com.flowable.common.transaction;

import com.flowable.common.cfg.CommandContext;

public interface TransactionContextFactory {

    TransactionContext openTransactionContext(CommandContext commandContext);
}
