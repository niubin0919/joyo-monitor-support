/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.model.send;

import com.joyotime.net.monitor.exchange.dao.model.send.base.BaseSend;

/**
 * 
 * @author nbin
 * @version $Id: Paibo.java, v 0.1 2019年1月28日 下午5:50:55 nbin Exp $
 */
public class Paibo extends BaseSend{

    /**
     * @param sn
     * @param platformId
     * @param storeId
     * @param content
     */
    public Paibo(String sn, long platformId, long storeId, String content) {
        super(sn, platformId, storeId, content);
    }

}
