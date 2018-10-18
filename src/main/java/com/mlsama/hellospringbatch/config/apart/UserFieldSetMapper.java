/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.apart;

/**
 * @author molong
 * @version Id: UserFieldSetMapper.java, v 0.1 2018/9/29 16:57 Tisson Exp $$
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
@Slf4j
public class UserFieldSetMapper implements FieldSetMapper<User> {

    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        log.info("****************helloWorld************************");
        return new User(fieldSet.readString("id"),
                fieldSet.readString("name"),
                fieldSet.readString("age"));
    }

}
