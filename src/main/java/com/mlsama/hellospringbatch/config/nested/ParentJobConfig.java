/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.nested;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 父Job,调用子job
 *
 * @author molong
 * @version Id: HelloJobConfig.java, v 0.1 2018/7/2 10:01 Tisson Exp $$
 */
@Configuration  //声明为配置类,相当于<beans>标签
@Slf4j
public class ParentJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private Job childJob1;
    @Autowired
    private Job childJob2;
    @Autowired
    private JobLauncher jobLauncher;

    /**
     * 创建父Job: parentJob
     * @param jobRepository
     * @param transactionManager
     * @return
     */
    @Bean
    public Job parentJob(JobRepository jobRepository,PlatformTransactionManager transactionManager){
        return jobBuilderFactory.get("parentJob")
        .start(childJob1(jobRepository,transactionManager)) //执行子job的step
        .next(childJob2(jobRepository,transactionManager))  //执行子job的step
        .build();
    }

    private Step childJob1(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
        .job(childJob1)
        .launcher(jobLauncher)  //job执行器
        .repository(jobRepository)  //job信息仓库
        .transactionManager(transactionManager) //事务
        .build();
    }


    private Step childJob2(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2"))
        .job(childJob2)
        .launcher(jobLauncher)
        .repository(jobRepository)
        .transactionManager(transactionManager)
        .build();
    }

}

