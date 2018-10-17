/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.apart;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源: c3p0
 *
 * @author molong
 * @version Id: DataSourceConfiguration.java, v 0.1 2018/10/03 9:44 Tisson Exp $$
 */


@Configuration      //声明为配置类
public class DataSourceConfiguration {

    @Bean(name = "dataSource")  //对象及名称
    @Primary        //主要的候选者
    //配置属性,prefix : 前缀 spring.datasource固定
    @ConfigurationProperties(prefix = "spring.datasource.c3p0")
    public DataSource createDataSource(){
        return DataSourceBuilder.create() // 创建数据源构建对象
                .type(ComboPooledDataSource.class) // 设置数据源类型
                .build(); // 构建数据源对象

    }
}
