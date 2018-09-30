/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Step级别的监听器(使用注解)
 *
 * @author molong
 * @version Id: StepChunkListener.java, v 0.1 2018/7/3 10:58 Tisson Exp $$
 */
@Slf4j
public class StepChunkListener {

    @BeforeChunk
    public void befoer(ChunkContext chunkContext){
        log.info("*********** StepChunkListener before *************");
    }

    @AfterChunk
    public void after(ChunkContext chunkContext){
        log.info("*********** StepChunkListener after *************");
    }

}

