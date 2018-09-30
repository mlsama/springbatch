/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.reader.fromXmlFile;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

/**
 * 从xml文件读取数据
 *
 * @author molong
 * @version Id: FromDBConfig.java, v 0.1 2018/7/19 13:55 Tisson Exp $$
 */
@Slf4j
@Configuration
public class FromXmlFileConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    //创建Job,名称:fromXml,并交给spring管理
    @Bean
    public Job fromXml(){
        return jobBuilderFactory.get("fromXml")
                .start(fromXmlStep())
                .build();
    }

    //创建Step,并交给spring管理
    @Bean
    public Step fromXmlStep() {
        return stepBuilderFactory.get("fromXmlStep")
                .<Person,Person>chunk(3)
                .reader(readerFromXml())
                .writer(writeXmlDate())
                .build();

    }

    /**
     * 写数据
     * @return
     */
    @Bean
    public ItemWriter<Person> writeXmlDate() {
        return (list) -> {
            for (Person person : list){
                log.info(person.toString());
            }
        };
    }

    /**
     * 从xml读数据
     * @return
     */
    @Bean
    @StepScope
    public StaxEventItemReader<Person> readerFromXml() {
        //读取xml文件的对象
        StaxEventItemReader<Person> reader = new StaxEventItemReader<>();
        //数据源
        reader.setResource(new ClassPathResource("person.xml"));
        //xml对象根元素名称
        reader.setFragmentRootElementName("person");
        //序列化对象
        XStreamMarshaller unMarshaller = new XStreamMarshaller();
        //设置xml与bean的映射
        Map<String,Class> map = new HashMap<>();
        map.put("person",Person.class);
        unMarshaller.setAliases(map);
        reader.setUnmarshaller(unMarshaller);
        return reader;
    }

}

