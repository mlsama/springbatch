/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.reader.FromDB;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 处理数据库读取的数据
 *
 * @author molong
 * @version Id: WriteDBDate.java, v 0.1 2018/7/19 14:51 Tisson Exp $$
 */
@Component("writeDBDate")
@Slf4j
public class WriteDBDate implements ItemWriter<BankBranch> {

    @Override
    public void write(List<? extends BankBranch> list) throws Exception {

        for (BankBranch bankBranch : list){
            log.info(bankBranch.toString());
        }
    }
}

