/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.schedule.autoSchedule;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * JobOperator自动调度: JobOperator是对JobLauncher的封装
 *
 * @author molong
 * @version Id: JobLaunchConfig.java, v 0.1 2018/9/27 17:13 Tisson Exp $$
 */
@Configuration
@Slf4j
public class JobOperatorAutoScheduleConfig implements StepExecutionListener {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> jobParams;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private ApplicationContext context;
    /**
     * 参数对象,只有参数跟以前都不一样的时候job才会执行
     */
    @Autowired
    private MyJobParametersIncrementer jobParametersIncrementer;

    @Bean
    public Job jobOperatorAutoSchedule() {
        return jobBuilderFactory.get("jobOperatorAutoSchedule")
                .incrementer(jobParametersIncrementer) //插入参数
                .start(jobOperatorAutoScheduleStep())
                .build();
    }

    @Bean
    public Step jobOperatorAutoScheduleStep() {
        return stepBuilderFactory.get("jobOperatorAutoScheduleStep")
                .listener(this) //step监听器,获取参数
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("jobOperatorAutoScheduleStep runs with param: " + jobParams.get("autoScheduleParam"));
                    return RepeatStatus.FINISHED;
                })).build();
    }
    /**
     * 设置调度规则
     */
    @Scheduled(fixedDelay = 5000)
    public void autoScheduleByTime() throws Exception{
        log.info("开始自动调用job:jobOperatorAutoSchedule");
        /**
         * 根据job名称调用
         */
        jobOperator().startNextInstance("jobOperatorAutoSchedule");
    }


    /**
     * 获取参数
     * @param stepExecution
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        jobParams = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    /**
     * 提供JobOperator
     * @return
     */
    @Bean
    public JobOperator jobOperator(){
        SimpleJobOperator operator = new SimpleJobOperator();
        operator.setJobLauncher(jobLauncher);
        operator.setJobParametersConverter(new DefaultJobParametersConverter());
        operator.setJobRepository(jobRepository);
        operator.setJobExplorer(jobExplorer);
        operator.setJobRegistry(jobRegistry);
        return operator;
    }

    /**
     * 提供JobRegistrar,通过job名称关联job对象
     * @return
     * @throws Exception
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistrar() throws Exception {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();

        postProcessor.setJobRegistry(jobRegistry);
        postProcessor.setBeanFactory(context.getAutowireCapableBeanFactory());
        postProcessor.afterPropertiesSet();

        return postProcessor;
    }
}

