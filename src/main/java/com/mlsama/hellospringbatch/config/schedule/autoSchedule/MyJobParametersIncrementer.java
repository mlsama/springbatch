/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.schedule.autoSchedule;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * job参数对象
 *
 * @author molong
 * @version Id: MyJobParametersIncrementer.java, v 0.1 2018/9/27 17:58 Tisson Exp $$
 */
@Component
public class MyJobParametersIncrementer implements JobParametersIncrementer{
    /**
     * 获取下一次job执行的参数,唯一
     * @param jobParameters
     * @return
     */
    @Override
    public JobParameters getNext(JobParameters jobParameters) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String param = format.format(new Date());
        JobParameters jobParameter = jobParameters == null ? new JobParameters() : jobParameters;
        return new JobParametersBuilder(jobParameter)
                        .addString("autoScheduleParam",param)
                        .toJobParameters();
    }
}

