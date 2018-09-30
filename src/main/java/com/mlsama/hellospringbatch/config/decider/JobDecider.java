/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.decider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Job的执行决策器:即多个Step的执行顺序和什么条件下执行
 *
 * @author molong
 * @version Id: JobDecider.java, v 0.1 2018/7/2 14:27 Tisson Exp $$
 */
@Configuration
@Slf4j
public class JobDecider {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建Step,并交给spring管理
    @Bean
    public Step first() {
        return stepBuilderFactory.get("Job decider first") //步骤名称
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** Job decider first ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step odd() {
        return stepBuilderFactory.get("Job decider odd")
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** Job decider odd ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step even() {
        return stepBuilderFactory.get("Job decider even")
                .tasklet((stepContribution,chunkContext) ->{
                    log.info("********************** Job decider even ****************");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }


    //创建Job,并交给spring管理
    @Bean
    public Job JobDecider(){
        return jobBuilderFactory.get("JobDecider")
        .start(first())
                /**
                 * next(Step step)
                 * next(JobExecutionDecider jobExecutionDecider)
                 */
        .next(myDecider())    //根据这个的执行返回结果决定执行下面2条中的哪一条,执行完选中的语句后,根据返回的结果,执行它后面的语句
        .from(myDecider()).on("ODD").to(odd())
        .from(myDecider()).on("EVEN").to(even())
        .from(odd()).on("*").to(myDecider()) //*匹配所有,所以重新执行next(myDecider),但是跳过这一句,执行end()
        .end()
        .build();

    }

    @Bean
    public JobExecutionDecider myDecider() {
        return new MyDecider();
    }


}

