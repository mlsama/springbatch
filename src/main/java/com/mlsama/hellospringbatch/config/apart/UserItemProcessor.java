/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.apart;

/**
 * @author molong
 * @version Id: UserItemProcessor.java, v 0.1 2018/9/29 17:00 Tisson Exp $$
 */
import org.springframework.batch.item.ItemProcessor;
public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User item) throws Exception {
        if (Integer.parseInt(item.getAge()) % 2 == 0) {
            return item;
        }
        return null;
    }

}