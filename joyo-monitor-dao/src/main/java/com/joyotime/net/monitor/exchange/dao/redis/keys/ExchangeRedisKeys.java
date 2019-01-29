/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.redis.keys;

/**
 * 
 * @author nbin
 * @version $Id: ExchangeRedisKeys.java, v 0.1 2019年1月22日 下午3:32:46 nbin Exp $
 */
public class ExchangeRedisKeys {
    
    public static final String  SPLIT  = "::";
    
    
    /**
     * 平台对应ftp地址 
     * @param platformId
     * @return
     */
    public static String getPlatformFtpKey(long platformId){
        return "platform_ftp:address:"+ platformId;
    }
    
    /**
     * 获得设备key
     * @param sn
     * @return
     */
    public static String getDeviceKey(String sn) {
        StringBuilder result = new StringBuilder();
        result.append("device").append(SPLIT).append(sn.toUpperCase());
        return result.toString();
    }
    
    
    
}
