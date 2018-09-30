/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * Job级别的监听器
 *      JobExecutionListener :是job级别的监听器接口
 *
 * @author molong
 * @version Id: JobExecuteListener.java, v 0.1 2018/7/3 10:27 Tisson Exp $$
 */
@Slf4j
public class JobExecuteListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("*********** JobExecuteListener before *************");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("*********** JobExecuteListener after *************");
    }
}

