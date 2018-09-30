/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.parameter;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Job 参数,继承StepExecutionListener,用于获取job执行时所需的参数
 *
 * @author molong
 * @version Id: HelloJobConfig.java, v 0.1 2018/7/3 11:42 Tisson Exp $$
 */
@Configuration  //声明为配置类,相当于<beans>标签
@Slf4j
public class JobParameterConfig implements StepExecutionListener {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters;

    //创建Job,名称:JobParameter,并交给spring管理
    @Bean
    public Job JobParameter(){
        return jobBuilderFactory.get("JobParameter")
                /**
                 * start是重载的方法:
                 *  1. start(Step step)
                 *  2. start(Flow flow)
                 */
                .start(step1()) //传入Step,执行step1
                .build();
    }

    //创建Step,并交给spring管理
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                /**
                 * 监听器,在执行这个步骤前获取所需参数
                 */
            .listener(this)     //谁调用step1()方法,this就是谁.在这里是本类
            .tasklet((stepContribution,chunkContext) ->{
                log.info("********************** "+parameters.get("parameter")+" ****************");
                return RepeatStatus.FINISHED;
        })
        .build();

    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        //获取参数
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

