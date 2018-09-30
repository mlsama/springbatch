/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.listener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

/**
 * 监听器,用于获取job运行是所需的参数
 *
 * @author molong
 * @version Id: HelloJobConfig.java, v 0.1 2018/7/2 10:01 Tisson Exp $$
 */
@Configuration  //声明为配置类,相当于<beans>标签
@Slf4j
public class ListenerConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    //创建Job,名称:JobListener,并交给spring管理
    @Bean
    public Job JobListener(){
        return jobBuilderFactory.get("JobListener")
                /**
                 * start是重载的方法:
                 *  1. start(Step step)
                 *  2. start(Flow flow)
                 */
                .start(step1())
                /**
                * job监听器:
                 *  listener(JobExecutionListener listener)
                 */
                .listener(new JobExecuteListener())
                .build();
    }

    //创建Step,并交给spring管理
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<String,String>chunk(2)    //每2个item为一批
            .faultTolerant()    //可容错
            .listener(new StepChunkListener())  //Step级别的监听器
            .reader(reader())       //读取数据
            .writer(writer())       //写数据
            .build();

    }

    @Bean
    public ItemWriter<String> writer() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String string : list){
                    log.info(string);
                }
            }
        };
    }

    @Bean
    public ItemReader<String> reader() {
        //从List集合读数据
        return new ListItemReader<>(Arrays.asList("first","second","third"));
    }

}

