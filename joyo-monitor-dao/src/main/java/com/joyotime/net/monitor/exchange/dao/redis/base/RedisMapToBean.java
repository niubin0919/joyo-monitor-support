/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.redis.base;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.joyotime.net.monitor.exchange.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * redis map to object
 * @author nbin
 * @version $Id: RedisMapToBean.java, v 0.1 2019年1月23日 下午8:01:06 nbin Exp $
 */
@Slf4j
public class RedisMapToBean {
    /**
     * hash to object
     * @param m
     * @param obj
     */
    public void mapToPojo(Map<String, String> m, Object obj) {
        String s = obj.getClass().getName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(s);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        for (Entry<String, String> entry : m.entrySet()) {
            String value = entry.getValue();
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            Field field = null;
            try {
                field = clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                if (field.getType() == Integer.class) {
                    if (value.contains(".")) {
                        value = value.substring(0, value.indexOf("."));
                    }
                    field.set(obj, Integer.valueOf(value));
                } else if (field.getType() == String.class) {
                    field.set(obj, entry.getValue());
                } else if (field.getType() == Date.class) {
                    if (value.indexOf(".") != -1) {
                        field.set(obj, DateUtils.parseDate(value, DateUtils.TIMESTAMP_FORMAT));
                    } else if (value.length() > 10) {
                        field.set(obj, DateUtils.parseDate(value, DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
                    } else {
                        field.set(obj, DateUtils.parseDate(value, DateUtils.DATE_FORMAT_YYYY_MM_DD));
                    }
                } else if (field.getType() == Double.class) {
                    field.set(obj, Double.parseDouble(value));
                } else if (field.getType() == Float.class) {
                    field.set(obj, Float.parseFloat(value));
                } else if (field.getType() == Long.class) {
                    if (value.contains(".")) {
                        value = value.substring(0, value.indexOf("."));
                    }
                    field.set(obj, Long.valueOf(value));
                } else if (field.getType() == Boolean.class) {
                    field.set(obj, Boolean.parseBoolean(value));
                } else if (field.getType() == Short.class) {
                    field.set(obj, Short.parseShort(value));
                } else if (field.getType() == BigDecimal.class) {
                    field.set(obj,  new BigDecimal(value));
                }
            } catch (Exception e) {
                log.error("redis RedisMapToBean.mapToPojo ! field:+"+field.getName()+"value:" + value);
            }
        }
    }
}
