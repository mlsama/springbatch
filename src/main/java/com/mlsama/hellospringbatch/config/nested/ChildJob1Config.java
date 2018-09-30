/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.nested;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 子Job
 *
 * @author molong
 * @version Id: HelloJobConfig.java, v 0.1 2018/7/2 10:01 Tisson Exp $$
 */
@Configuration  //声明为配置类,相当于<beans>标签
@Slf4j
public class ChildJob1Config {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    //创建Job,并交给spring管理
    @Bean
    public Job childJob1(){
        return jobBuilderFactory.get("childJob1Config")
                /**
                 * start是重载的方法:
                 *  1. start(Step step)
                 *  2. start(Flow flow)
                 */
        .start(childJob1ConfigStep())
        .build();
    }

    //创建Step,并交给spring管理
    @Bean
    public Step childJob1ConfigStep() {
        return stepBuilderFactory.get("childJob1ConfigStep")
        .tasklet((stepContribution,chunkContext) ->{
            log.info("********************** childJob1ConfigStep ****************");
            return RepeatStatus.FINISHED;
        })
        .build();

    }
}

