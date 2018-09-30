/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author molong
 * @version Id: JobLauncherController.java, v 0.1 2018/9/27 16:32 Tisson Exp $$
 */
@RestController
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {
    @Autowired
    private JobLauncher jobLauncher;    //项目启动Springbatch会创建这个对象,直接引用即可
    @Autowired
    private Job jobScheduledJob;        //JobLaunchConfig中定义的job

    @PostMapping("/jobLauncher")
    public String jobLauncherRun(@RequestParam("jobLauncherPara") String jobLauncherPara){
        log.info("开始执行job:{}",jobLauncher);
        //传送参数给job
        JobParameters jobParameters = new JobParametersBuilder()
                                        .addString("jobScheduledparam",jobLauncherPara)
                                        .toJobParameters();
        try {
            //手动执行job : jobScheduledJob
            jobLauncher.run(jobScheduledJob,jobParameters);
        } catch (Exception e) {
            log.error("执行job:{},发生异常{}",jobLauncher,e);
            return "执行job"+jobLauncher.toString()+"发生异常,参数是:"+jobLauncherPara;
        }
        return "执行job"+jobLauncher.toString()+"成功,参数是:"+jobLauncherPara;
    }


    @Autowired
    private JobOperator jobOperator;    //对JobLauncher的封装,需要手动创建

    @PostMapping("/jobOperator")
    public String jobOperatorRun(@RequestParam("jobOperatorParam") String jobOperatorParam){
        log.info("开始执行job:{}",jobOperator);
        //执行job
        try {
            /**
            * 通过job名称执行job,需要自定义JobRegistryBeanPostProcessor进行关联
             * 参数一:  job的名称
             * 参数二:  JobParameters  可以的对象,也可以是对应格式的数据
             */
            jobOperator.start("jobOperatorJob","jobOperatorParam="+jobOperatorParam);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "执行job"+jobOperator.toString()+"成功,参数是:"+jobOperatorParam;
    }

}

