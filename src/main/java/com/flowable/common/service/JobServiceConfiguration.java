package com.flowable.common.service;

import com.flowable.common.executor.CommandExecutor;
import lombok.Data;

@Data
public class JobServiceConfiguration {


    protected CommandExecutor commandExecutor;

}
