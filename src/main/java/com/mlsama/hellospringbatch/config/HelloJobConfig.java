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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * hello springbatch
 *
 * @author molong
 * @version Id: HelloJobConfig.java, v 0.1 2018/7/2 10:01 Tisson Exp $$
 */
@Configuration  //声明为配置类,相当于<beans>标签
@Slf4j
public class HelloJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    //创建Job,名称:helloSpringbatch,并交给spring管理
    @Bean
    public Job helloSpringbatch(){
        return jobBuilderFactory.get("hello springbatch")
                /**
                 * start是重载的方法:
                 *  1. start(Step step)
                 *  2. start(Flow flow)
                 */
        .start(step1()) //传入Step,执行step1
                /**
                 * 2种step执行流程
                 *      next
                 *      on
                 */
        .next(step2())
        .on("COMPLETED").to(step3()).from(step3()).end()
        .build();
    }

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
}

