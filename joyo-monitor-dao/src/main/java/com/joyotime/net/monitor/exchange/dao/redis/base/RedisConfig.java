/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.redis.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author nbin
 * @version $Id: FileRedisConfig.java, v 0.1 2019年1月15日 下午7:18:42 nbin Exp $
 */
@Slf4j
public class RedisConfig extends BaseConfig {
    
    protected static Properties pro;
    
    static {
        BufferedReader reader = null;
        try {
            pro = new Properties();
            InputStream fs = RedisConfig.class.getClassLoader().getResourceAsStream("redis.properties");
            pro.load(fs);
        } catch (IOException e) {
            log.info("redis.properties error...loading classPath...");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 获取redis操作默认数据库索引号
     * @return
     */
    public static int getDbIndex() {
        int dbidnex = getSetInt(pro, "redis.dbindex");
        if (dbidnex == -1) {
            dbidnex = 0;
        }
        return dbidnex;
    }

    /**
     * 获取redis连接超时时间（毫秒） 
     * @return
     */
    public static int getTimeout() {
        int intTimeout = getSetInt(pro, "redis.timeout");
        if (intTimeout == -1) {
            intTimeout = 10000;
        }
        return intTimeout;
    }

    /**
     * 获取redis模式（1=单例，2=集群） 
     * @return
     */
    public static int getModel() {
        int intTimeout = getSetInt(pro, "redis.model");
        if (intTimeout == -1) {
            intTimeout = 1;
        }
        return intTimeout;
    }

    /**
     * 获取Redis服务器地址（单例）
     * @return
     */
    public static String getSingleHost() {
        String host = pro.getProperty("redis.single.host");
        if (host == null) {
            host = "127.0.0.1";
        }
        return host;
    }

    /**
     * 获取Redis服务器端口（单例）
     * @return
     */
    public static int getSinglePost() {
        int port = getSetInt(pro, "redis.single.port");
        if (port == -1) {
            port = 6379;
        }
        return port;
    }

    /**
     * 获取Redis服务器密码（单例）
     * @return
     */
    public static String getSinglePassword() {
        return pro.getProperty("redis.single.password");
    }

    /**
     * 获取Redis哨兵ip地址（集群）
     * @return
     */
    public static Set<String> getClusterSentinelsIps() {
        Set<String> set = new TreeSet<String>();
        String[] ips = pro.getProperty("redis.cluster.sentinels.ips").split(",");
        CollectionUtils.addAll(set, ips);
        return set;
    }

    /**
     * 获取Redis主库名称（集群）
     * @return
     */
    public static String getClusterMasterName() {
        return pro.getProperty("redis.cluster.masterName");
    }

    /**
     * 获取Redis通用密码（集群）
     * @return
     */
    public static String getClusterPassword() {
        return pro.getProperty("redis.cluster.password");
    }
    
    /**
     * 获取redis连接池最大连接数配置
     * @return
     */
    public static int getPoolMaxActive() {
        int intMaxActive = getSetInt(pro, "redis.pool.maxActive");
        if (intMaxActive == -1) {
            intMaxActive = 100;
        }
        return intMaxActive;
    }

    /**
     * 获取redis连接池最大空闲数配置
     * @return
     */
    public static int getPoolMaxIdle() {
        int intMaxIdle = getSetInt(pro, "redis.pool.maxIdle");
        if (intMaxIdle == -1) {
            intMaxIdle = 50;
        }
        return intMaxIdle;
    }

    /**
     * 获取redis连接池最小空闲数配置
     * @return
     */
    public static int getPoolMinIdle() {
        int intMinIdle = getSetInt(pro, "redis.pool.minIdle");
        if (intMinIdle == -1) {
            intMinIdle = 50;
        }
        return intMinIdle;
    }

    /**
     * 获取redis连接池最大等待时间（毫秒） 
     * @return
     */
    public static int getPoolMaxWait() {
        int intMaxWait = getSetInt(pro, "redis.pool.maxWait");
        if (intMaxWait == -1) {
            intMaxWait = 10000;
        }
        return intMaxWait;
    }

    /**
     * 获取redis是否检测取出的连接 
     * @return
     */
    public static boolean getPoolTestOnBorrow() {
        return getSetBool(pro, "reids.pool.testOnBorrow");
    }

    /**
     * 获取redis是否检测返回的连接
     * @return
     */
    public static boolean getPoolTestOnReturn() {
        return getSetBool(pro, "reids.pool.testOnReturn");
    }

    /**
     * 定时对连接池空的空闲连接进行检查校验
     * @return
     */
    public static boolean getPoolTestWhileIdle() {
        return getSetBool(pro, "redis.pool.testWhileIdle");
    }

    /**
     * 定时对连接池空的空闲连接进行检查校验的时间间隔
     * @return
     */
    public static long getPoolTimeBetweenEvictionRunsMillis() {
        return getSetLong(pro, "redis.pool.timeBetweenEvictionRunsMillis");
    }

    /**
     * 定时检查时，每次检查的对象数
     * @return
     */
    public static int getPoolNumTestsPerEvictionRun() {
        return getSetInt(pro, "redis.pool.numTestsPerEvictionRun");
    }

    /**
     * 定时检查时，对象空闲多久才会被驱逐
     * @return
     */
    public static int getPoolMinEvictableIdleTimeMillis() {
        return getSetInt(pro, "redis.pool.minEvictableIdleTimeMillis");
    }

    /**
     * 空闲多长时间（毫秒）被逐出
     * @return
     */
    public static long getPoolSoftMinEvictableIdleTimeMillis() {
        return getSetLong(pro, "redis.pool.softMinEvictableIdleTimeMillis");
    }
}   
