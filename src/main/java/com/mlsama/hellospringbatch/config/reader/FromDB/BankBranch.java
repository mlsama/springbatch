/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.mlsama.hellospringbatch.config.reader.FromDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author molong
 * @version Id: BankBranch.java, v 0.1 2018/7/19 14:06 Tisson Exp $$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankBranch {
    /** 系统流水号 */
    private String logSeq;

    /** 交易金额 */
    private String amount;

    /** 交易日期 YYYYMMDD */
    private String tradeDate;

    /** 交易时间 hhmmss*/
    private String tradeTime;

    /** 外系统流水号 */
    private String extSeq;

    /** 响应码 */
    private String rspCode;

    /** 银行返回码 */
    private String bankRspCode;

    /** 银行返回码描述 */
    private String bankRspDesc;

    /** 订单编码 */
    private String orderCode;

    /** 支付方流水 */
    private String paySeq;
}

