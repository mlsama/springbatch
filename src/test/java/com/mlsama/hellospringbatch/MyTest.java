/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch;

import com.mlsama.hellospringbatch.utils.UncompressZFileUtil;
import org.junit.Test;

import java.io.File;

/**
 * @author molong
 * @version Id: MyTest.java, v 0.1 2018/8/6 17:39 Tisson Exp $$
 */
public class MyTest {

    @Test
    public void fileTest() throws Exception {
        UncompressZFileUtil.unCompress("C:/Users/Tisson/Desktop/clsun/IND18080301ACOMN.Z"
                ,"C:/Users/Tisson/Desktop/clsun/IND18080301ACOMN1");
    }

    @Test
    public void stringTest(){
        String s = "48379219    00049992    747302 0803162152 6216261000000000018 000000000100 000000000000  00000000000 0200 000000 1000 01080209 777290058160810 215228025551 00 000000 90880051    000000 00 012 000000000000 000000000007  00000000003 1 000 0 0 0000000000 90880051    0 07 10  00000000000  000201 0000                          SZUP032018080316215228025551             0401";
    }

    @Test
    public void zipSize(){
        File zip = new File("G://IND18070201ACOMN.Z");
        if (zip.exists()){

            System.out.println(zip.length());
        }
    }
}

