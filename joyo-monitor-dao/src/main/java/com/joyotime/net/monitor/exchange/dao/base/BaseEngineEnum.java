/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.base;

/**
 * 
 * 平台标记<p>
 * 如果不同平台业务相同最好还是用不同的标记id，在把提交的文件push到redis buffer key会用到，如果重复不好区分是那个平台的业务
 * @author nbin
 * @version $Id: PlatformEngineEnum.java, v 0.1 2019年1月8日 下午3:17:09 nbin Exp $
 */
public enum BaseEngineEnum {
    fenghuo(                1,    "烽火",             "fenghuo"),
    fenghuo_suining(        3,    "烽火-遂宁",         "fenghuo_suining"),
    yunceng(                4,    "上海云辰",          "yunceng"),
    paibo(                  5,    "派博平台",          "paibo"),
    paibo_baimi(            6,    "派博平台ID(百米)",  "paibo_baimi"),
    zhongxin(               7,    "中新赛克",          "zhongxin"),
    ;
   
    private long flag;
    private String name;
    private String queueName;
    
    private BaseEngineEnum(long flag, String name,String queueName) {
        this.flag = flag;
        this.name = name;
        this.queueName = queueName;
    }
    
    public static boolean checkEnum(long code){
        BaseEngineEnum prop = getPlatformEngineEnum(code);
        if(null == prop){
            return false;
        }
        return true;
    }
    
    public static BaseEngineEnum getPlatformEngineEnum(long flag) {
        for (BaseEngineEnum platform : BaseEngineEnum.values()) {
            if (flag == platform.flag) {
                return platform;
            }
        }
        return null;
    }
    
    public static BaseEngineEnum getPlatformEngineEnum(String queueName) {
        for (BaseEngineEnum platform : BaseEngineEnum.values()) {
            if (queueName.equals(platform.getQueueName())) {
                return platform;
            }
        }
        return null;
    }

    public long getFlag() {
        return flag;
    }
    public String getName() {
        return name;
    }
    public String getQueueName() {
        return queueName;
    }
}
