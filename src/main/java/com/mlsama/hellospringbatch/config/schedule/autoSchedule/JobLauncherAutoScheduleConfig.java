/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.schedule.autoSchedule;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * JobLauncher自动调度
 * @author molong
 * @version Id: JobLaunchConfig.java, v 0.1 2018/9/27 18:13 Tisson Exp $$
 */
@Configuration
@Slf4j
public class JobLauncherAutoScheduleConfig implements StepExecutionListener {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> jobParams;

    @Autowired
    private JobLauncher jobLauncher;    //项目启动Springbatch会创建这个对象,直接引用即可

    /**
     * 参数对象,只有参数跟以前都不一样的时候job才会执行
     */
    @Autowired
    private MyJobParametersIncrementer jobParametersIncrementer;

    @Bean
    public Job jobLauncherAutoSchedule() {
        return jobBuilderFactory.get("jobLauncherAutoSchedule")
                //.incrementer(jobParametersIncrementer)    //不在这里插入参数
                .start(jobLauncherAutoScheduleStep())
                .build();
    }

    @Bean
    public Step jobLauncherAutoScheduleStep() {
        return stepBuilderFactory.get("jobLauncherAutoScheduleStep")
                .listener(this) //step监听器,获取参数
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("jobLauncherAutoScheduleStep runs with param: " + jobParams.get("autoScheduleParam"));
                    return RepeatStatus.FINISHED;
                })).build();
    }
    /**
     * 设置调度规则
     */
    //@Scheduled(fixedDelay = 5000)   //5秒执行一次
    @Scheduled(cron = "0/2 * * * * ?")  //corn表达式
    public void autoScheduleByTime() throws Exception{
        log.info("开始自动调用job:jobLauncherAutoSchedule");
        /**
         * 参数一:  job对象
         * 参数二:  参数
         */
        jobLauncher.run(jobLauncherAutoSchedule(),jobParametersIncrementer.getNext(null));
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

