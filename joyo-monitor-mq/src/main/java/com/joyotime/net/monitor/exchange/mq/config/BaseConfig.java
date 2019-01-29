/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.config;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * base config
 * 
 * @author nbin
 * @version $Id: BaseConfig.java, v 0.1 2019年1月23日 下午7:19:32 nbin Exp $
 */
@Slf4j
public abstract class BaseConfig {

    protected static int getSetInt(Properties pro, String name) {
        int setting = -1;
        try {
            String sett = pro.getProperty(name);
            setting = Integer.parseInt(sett);
        } catch (Exception e) {
            log.warn("getSetInt-" + name + " error：\n" + e.getMessage(), e);
        }
        return setting;
    }

    protected static long getSetLong(Properties pro, String name) {
        long setting = -1;
        try {
            String sett = pro.getProperty(name);
            setting = Long.parseLong(sett);
        } catch (Exception e) {
            log.warn("getSetLong-" + name + " error：\n" + e.getMessage(), e);
        }
        return setting;
    }

    protected static boolean getSetBool(Properties pro, String name) {
        boolean setting = false;
        try {
            String sett = pro.getProperty(name);
            setting = Boolean.parseBoolean(sett);
        } catch (Exception e) {
            log.warn("getSetBool-" + name + " error：\n" + e.getMessage(), e);
        }
        return setting;
    }
}
