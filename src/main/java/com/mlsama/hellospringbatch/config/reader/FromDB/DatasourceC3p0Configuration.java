/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.reader.FromDB;

/**
 * c3p0配置类
 *
 * @author molong
 * @version Id: DatasourceC3p0Configuration.java, v 0.1 2018/7/20 15:15 Tisson Exp $$
 */

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceC3p0Configuration {
      @Bean(name = "dataSource")
      @Qualifier(value = "dataSource")
      @Primary
      @ConfigurationProperties(prefix = "c3p0")
      public DataSource dataSource() {
          return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
      }
 }

