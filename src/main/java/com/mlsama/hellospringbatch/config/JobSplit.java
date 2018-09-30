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
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * 异步处理多个job
 *
 * @author molong
 * @version Id: JobSplit.java, v 0.1 2018/7/2 11:39 Tisson Exp $$
 */
@Configuration
@Slf4j
public class JobSplit {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建Step,并交给spring管理
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("JobSplit step1") //步骤名称
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** JobSplit step1 ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("JobSplit step2")
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** JobSplit step2 ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }


    //创建JobSplit,与创建Job类似,并交给spring管理
    @Bean
    public Flow createFlow1(){
        return new FlowBuilder<Flow>("JobSplit1") //Flow的名称
                .start(step1())
                .build();
    }

    //创建JobSplit,与创建Job类似,并交给spring管理
    @Bean
    public Flow createFlow2(){
        return new FlowBuilder<Flow>("JobSplit2") //Flow的名称
                .start(step2())
                .build();
    }


    //创建Job,并交给spring管理
    @Bean
    public Job JobSplit(){
        return jobBuilderFactory.get("JobSplit")
                /**
                 * start是重载的方法:
                 *  1. start(Step step)
                 *  2. start(Flow flow)
                 */
                .start(createFlow1())    //传入Flow
                /**
                 * split(TaskExecutor executor) : 异步处理其他job
                 * add(Flow... flows)  : 参数是可变参数,可以接受多个Flow
                 */
                .split(new SimpleAsyncTaskExecutor()).add(createFlow2())
                .end()      //需要这个方法
                .build();
    }
}

