package com.mlsama.hellospringbatch.config.reader.FromDB;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication	//项目启动
@EnableBatchProcessing	//加载所有的job
public class FormDBApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormDBApplication.class, args);
	}
}
