/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Job  Flow(流)
 *
 * @author molong
 * @version Id: JobFlowConfig.java, v 0.1 2018/7/2 10:53 Tisson Exp $$
 */
@Configuration
@Slf4j
public class JobFlowConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建Step,并交给spring管理
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** step1 ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** step2 ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** step3 ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    //创建JobFlow,与创建Job类似,并交给spring管理
    @Bean
    public Flow createFlow(){
        return new FlowBuilder<Flow>("JobFlow") //Flow的名称
        .start(step1())
        .next(step2())
        .next(step3())
        .build();
    }


    //创建Job,并交给spring管理
    @Bean
    public Job jobFlow(){
        return jobBuilderFactory.get("Job Flow")
                /**
                 * start是重载的方法:
                 *  1. start(Step step)
                 *  2. start(Flow flow)
                 */
                .start(createFlow())    //传入Flow
                .end()      //需要这个方法
                .build();
    }
}

