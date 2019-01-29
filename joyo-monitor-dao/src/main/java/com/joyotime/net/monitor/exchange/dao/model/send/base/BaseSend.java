/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.model.send.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author nbin
 * @version $Id: BaseSend.java, v 0.1 2019年1月28日 下午7:20:35 nbin Exp $
 */
@Setter
@Getter
@ToString
public class BaseSend {
    
    /**
     * 设备号
     */
    private String sn;
    /**
     * 平台id
     */
    private long platformId;
    /**
     * 商铺id
     */
    private long storeId;
    /**
     * 发送消息内容
     */
    private String content;
    /**
     * @param sn
     * @param platformId
     * @param storeId
     * @param content
     */
    public BaseSend(String sn, long platformId, long storeId, String content) {
        super();
        this.sn = sn;
        this.platformId = platformId;
        this.storeId = storeId;
        this.content = content;
    }
    
    
    
}
