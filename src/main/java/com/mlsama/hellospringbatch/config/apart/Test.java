/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.apart;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author molong
 * @version Id: Test.java, v 0.1 2018/9/29 17:10 Tisson Exp $$
 */
public class Test {

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("myJobApplication.xml");
        SimpleJobLauncher launcher = (SimpleJobLauncher) ctx.getBean("launcher");
        Job job = (Job) ctx.getBean("jobExample");
        System.out.println(launcher);
        System.out.println(job);
        //参数设置方式一
        // launcher.run(job, new RunIdIncrementer().getNext(null));
        //参数设置方式二
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sf.format(new Date());
        launcher.run(
                job,
                new JobParametersBuilder()
                        .addString("date", date)
                        .toJobParameters());
        ctx.close();


    }
}
