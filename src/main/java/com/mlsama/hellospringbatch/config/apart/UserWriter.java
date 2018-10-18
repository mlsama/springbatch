/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.apart;

/**
 * @author molong
 * @version Id: UserWriter.java, v 0.1 2018/9/29 17:08 Tisson Exp $$
 */
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class UserWriter implements ItemWriter<User> {

    @Override
    public void write(List<? extends User> items) throws Exception {
        log.info("****************helloWorld************************");
        for(User user : items){
            log.info("处理的对象是:{}",user);
        }
    }

}