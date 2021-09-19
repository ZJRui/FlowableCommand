package com.flowable.common.cfg;

import com.flowable.common.transaction.TransactionContext;
import com.flowable.common.transaction.TransactionContextHolder;
import org.apache.poi.ss.formula.functions.T;

import java.util.Stack;

public class Context {


    protected static ThreadLocal<Stack<CommandContext>> commandContextThreadLocal = new ThreadLocal<>();

    public static CommandContext getCommandContext(){
        Stack<CommandContext> stack = getStack(commandContextThreadLocal);
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public static void setCommandContext(CommandContext commandContext) {
        getStack(commandContextThreadLocal).push(commandContext);
    }

    public static void removeCommandContext(){
        getStack(commandContextThreadLocal).pop();
    }
    public static TransactionContext getTransactionContext(){
        return TransactionContextHolder.getTransactionContext();
    }

    public static void setTransactionContext(TransactionContext transactionContext) {
        TransactionContextHolder.setTransactionContext(transactionContext);

    }
    public static  void removeTransactionContext(){
        TransactionContextHolder.removeTransactionContext();

    }

    protected static <T> Stack<T> getStack(ThreadLocal<Stack<T>> threadLocal) {

        Stack<T> ts = threadLocal.get();
        if (ts == null) {
            ts = new Stack<>();
            threadLocal.set(ts);
        }
        return ts;
    }


}
