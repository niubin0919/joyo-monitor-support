/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author nbin
 * @version $Id: ActiveConfig.java, v 0.1 2019年1月23日 下午7:08:06 nbin Exp $
 */
@Slf4j
public class ActivemqConfig extends BaseConfig {
    
    protected static Properties pro;
    
    static {
        try {
            pro = new Properties();
            InputStream fs = ActivemqConfig.class.getClassLoader().getResourceAsStream("conf/mq-config.properties");
            pro.load(fs);
        } catch (IOException e) {
            log.info("loading....sdk-config.properties error...!", e);
        }
    }

    /**
     * 获取客户端ID
     * 
     * @return
     */
    public static String getClientID() {
        return pro.getProperty("activemq.ClientID");
    }

    /**
     * 获取MQ连接地址
     * 
     * @return
     */
    public static String getMqURI() {
        return pro.getProperty("activemq.URI");
    }

    /**
     * 获取MQ队列用户名
     * 
     * @return
     */
    public static String getUid() {
        return pro.getProperty("activemq.Uid");
    }

    /**
     * 获取MQ队列密码
     * 
     * @return
     */
    public static String getPwd() {
        return pro.getProperty("activemq.Pwd");
    }

    /**
     * 获取本地错误数据缓存大小
     * 
     * @return
     */
    public static int getLocalBuffSize() {
        int size = getSetInt(pro, "activemq.LocalBuffSize");
        return size == -1 ? 5000 : size;
    }
}
