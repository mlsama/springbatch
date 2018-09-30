package com.mlsama.hellospringbatch.config.schedule.autoSchedule;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling   //开启自动调度
public class AutoScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoScheduleApplication.class, args);
    }
}
