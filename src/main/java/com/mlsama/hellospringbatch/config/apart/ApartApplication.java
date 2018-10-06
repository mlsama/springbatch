package com.mlsama.hellospringbatch.config.apart;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication	//项目启动
//@EnableBatchProcessing	//加载所有的job,与quarter整合调度的时候不能有这个
@ImportResource(locations = "classpath:/myJobApplication.xml")
public class ApartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartApplication.class, args);
	}
}
