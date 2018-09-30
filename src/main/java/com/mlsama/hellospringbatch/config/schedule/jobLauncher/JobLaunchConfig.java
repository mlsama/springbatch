/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.schedule.jobLauncher;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * job调度: JobLauncher手动触发job
 *
 * @author molong
 * @version Id: JobLaunchConfig.java, v 0.1 2018/9/27 16:13 Tisson Exp $$
 */
@Configuration
public class JobLaunchConfig implements StepExecutionListener {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> jobParams;

    @Bean
    public Job jobScheduledJob() {
        return jobBuilderFactory.get("jobScheduledJob")
                .start(jobScheduledStep())
                .build();
    }

    @Bean
    public Step jobScheduledStep() {
        return stepBuilderFactory.get("jobScheduledStep")
                .listener(this) //step监听器,获取参数
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("jobScheduledStep runs with param: " + jobParams.get("jobScheduledparam"));
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        jobParams = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

