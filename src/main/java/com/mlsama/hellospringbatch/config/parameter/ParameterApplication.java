package com.mlsama.hellospringbatch.config.parameter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication	//项目启动
@EnableBatchProcessing	//加载所有的job
public class ParameterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParameterApplication.class, args);
	}
}
