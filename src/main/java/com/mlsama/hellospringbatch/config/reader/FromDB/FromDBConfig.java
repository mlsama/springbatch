/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.reader.FromDB;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 从数据库读取数据
 *
 * @author molong
 * @version Id: FromDBConfig.java, v 0.1 2018/7/19 13:55 Tisson Exp $$
 */
@Slf4j
@Configuration
public class FromDBConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemWriter<BankBranch> writeDBDate;
    @Autowired
    private DataSource dataSource;

    //创建Job,名称:helloSpringbatch,并交给spring管理
    @Bean
    public Job fromDB(){
        return jobBuilderFactory.get("fromDB")
                .start(fromDBStep())
                .build();
    }

    //创建Step,并交给spring管理
    @Bean
    public Step fromDBStep() {
        return stepBuilderFactory.get("fromDBStep")
                .<BankBranch,BankBranch>chunk(10)
                .reader(readerFromDB())
                //.writer(writeDBDate)
                .writer(writeDBDate())
                .build();

    }

    /**
     * 写数据
     * @return
     */
    @Bean
    public ItemWriter<BankBranch> writeDBDate() {
        return (list) -> {
            for (BankBranch bankBranch : list){
                log.info(bankBranch.toString());
            }
        };
    }

    /**
     * 从数据库读数据
     * @return
     */
    @Bean
    @StepScope
    public JdbcPagingItemReader<BankBranch> readerFromDB() {
        //分页读取数据库的对象
        JdbcPagingItemReader<BankBranch> reader = new JdbcPagingItemReader();
        //每次读的数据
        reader.setFetchSize(10);
        //数据源
        reader.setDataSource(dataSource);
        //每行数据进行映射
        reader.setRowMapper(new RowMapper<BankBranch>() {
            @Nullable
            @Override
            public BankBranch mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new BankBranch(
                        resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),
                        resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),
                        resultSet.getString(10)
                );
            }
        });
        //查询对象
        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        //查询字段
        queryProvider.setSelectClause("*");
        //表
        queryProvider.setFromClause("T_PGW_DEALLOG");
        //排序字段集合
        Map<String,Order> sortKeys = new HashMap<>();
        //id升序
        sortKeys.put("LOG_SEQ",Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);
        return reader;
    }

}

