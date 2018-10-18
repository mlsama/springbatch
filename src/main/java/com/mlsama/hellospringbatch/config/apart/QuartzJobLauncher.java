package com.mlsama.hellospringbatch.config.apart;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * desc:
 * author：mlsama
 * dataTime:2018/10/4 21:35
 */
@Slf4j
public class QuartzJobLauncher extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Job job = (Job)jobDataMap.get("job");
        JobLauncher jobLauncher = (JobLauncher) jobDataMap.get("jobLauncher");
        //MapJobRegistry jobRegistry = (MapJobRegistry)jobDataMap.get("jobRegistry");
        log.info("job :{}",job);
        log.info("jobLauncher :{}",jobLauncher);
        //log.info("jobRegistry :{}",jobRegistry);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sf.format(new Date());
        try {
            log.info("Current Time :{},job:{}",date,job);
            jobLauncher.run(
                    job,new JobParameters());
                    //new JobParametersBuilder().addString("date", date).toJobParameters());
            log.info("**********************job:{}执行完毕****************",job);
        } catch (Exception e) {
            log.error("发生异常{}",e);
        }
    }
}
