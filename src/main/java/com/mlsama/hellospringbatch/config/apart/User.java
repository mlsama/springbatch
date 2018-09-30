/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.apart;

import lombok.Data;

/**
 * @author molong
 * @version Id: User.java, v 0.1 2018/9/29 16:58 Tisson Exp $$
 */
@Data
public class User {
    private String id;
    private String name;
    private String age;

    public User(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

