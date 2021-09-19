package com.flowable.common.cfg;

import com.flowable.common.cfg.TransactionPropagation;

public class CommandConfig {


    private boolean contextReusePossible;

    private TransactionPropagation propagation;

    public CommandConfig(){
        this.contextReusePossible=true;
        this.propagation=TransactionPropagation.REQUIRED;
    }
    public CommandConfig(boolean contextReusePossible){
        this.contextReusePossible=contextReusePossible;
        this.propagation=TransactionPropagation.REQUIRED;

    }
    public CommandConfig(boolean contextReusePossible, TransactionPropagation propagation){
        this.contextReusePossible=contextReusePossible;
        this.propagation=propagation;
    }

    public boolean isContextReusePossible() {
        return contextReusePossible;
    }

    public void setContextReusePossible(boolean contextReusePossible) {
        this.contextReusePossible = contextReusePossible;
    }

    public TransactionPropagation getPropagation() {
        return propagation;
    }

    public void setPropagation(TransactionPropagation propagation) {
        this.propagation = propagation;
    }
}
